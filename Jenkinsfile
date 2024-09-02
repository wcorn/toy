def PREVIOUS_TAG = ''
def ROLLBACK_NEEDED = 'false'
def CURRENT_STAGE = ''

pipeline {
    agent any
    environment {
        DISCORD_WEBHOOK = credentials('discord-webhook')
        VAULT_TOKEN = credentials('vault-token')
        VAULT_URL = credentials('vault-url')
        VAULT_PORT = credentials('vault-port')
    }
    stages {
        stage('Checkout') {
            steps {
                script {
                    CURRENT_STAGE = env.STAGE_NAME
                    git branch: 'main', url: 'https://github.com/wcorn/toy.git'
                }
            }
        }
        stage('Get Previous Docker Tag') {
            steps {
                script {
                    CURRENT_STAGE = env.STAGE_NAME
                    PREVIOUS_TAG = sh(script: """
                        docker images | awk '\$1 == "${env.IMAGE_NAME}" {print \$2}' | sort -V | tail -n 1
                    """, returnStdout: true).trim()
                }
            }
        }
        stage('Build Spring Boot Application') {
            steps {
                script {
                    CURRENT_STAGE = env.STAGE_NAME
                    sh './gradlew clean build'
                }
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    CURRENT_STAGE = env.STAGE_NAME
                    def image = docker.build("${env.IMAGE_NAME}:${env.BUILD_NUMBER}", ".")
                }
            }
        }
        stage('Deploy Container') {
            steps {
                script {
                    CURRENT_STAGE = env.STAGE_NAME
                    sh """
                        docker stop ${env.CONTAINER_NAME} || true
                        docker rm ${env.CONTAINER_NAME} || true
                        docker run -d --name ${env.CONTAINER_NAME} \\
                            -e VAULT_TOKEN=${VAULT_TOKEN} \\
                            -e VAULT_URL=${VAULT_URL} \\
                            -e VAULT_PORT=${VAULT_PORT} \\
                            -v ${env.TOY_VOLUME}:/var/log/spring \\
                            -p ${env.PORT}:${env.PORT} \\
                            -p ${env.MON_PORT}:${env.MON_PORT} \\
                            ${env.IMAGE_NAME}:${env.BUILD_NUMBER}
                    """

                    retries = 6
                    healthStatus = ''
                    for (int i = 0; i < retries; i++) {
                        sleep(30)
                        healthStatus = sh(script: "curl -s -o /dev/null -w '%{http_code}' ${env.BASE_URL}:${env.MON_PORT}${env.HEALTHCHECK_PATH}", returnStdout: true).trim()
                        if (healthStatus == '200') {
                            break;
                        }
                    }
                    if (healthStatus != '200') {
                        ROLLBACK_NEEDED = 'true'
                        error("Health check failed for ${env.CONTAINER_NAME}")
                    }
                }
            }
        }
    }
    post {
        failure {
            script {
                if (ROLLBACK_NEEDED == 'true') {
                    sh """
                        docker stop ${env.CONTAINER_NAME} || true
                        docker rm ${env.CONTAINER_NAME} || true
                        docker rmi ${env.IMAGE_NAME}:${env.BUILD_NUMBER} || true
                        docker run -d --name ${env.CONTAINER_NAME} \\
                            -e VAULT_TOKEN=${VAULT_TOKEN} \\
                            -e VAULT_URL=${VAULT_URL} \\
                            -e VAULT_PORT=${VAULT_PORT} \\
                            -v ${env.TOY_VOLUME}:/var/log/spring \\
                            -p ${env.PORT}:${env.PORT} \\
                            -p ${env.MON_PORT}:${env.MON_PORT} \\
                            ${env.IMAGE_NAME}:${PREVIOUS_TAG} || true
                    """
                }
                notifyDiscord('Failure', "Failed at stage: ${CURRENT_STAGE}")
            }
        }
        success {
            script {
                sh "docker rmi ${env.IMAGE_NAME}:${PREVIOUS_TAG} || true"
                notifyDiscord('Success', 'Build and deployment successful')
            }
        }
        cleanup {
            script {
                deleteDir()
            }
        }
    }
}
def notifyDiscord(String result, String description) {
    discordSend description: """
        Title: ${currentBuild.displayName}
        Result: ${description}
        Duration: ${currentBuild.duration / 1000}s
    """,
    link: env.BUILD_URL,
    result: currentBuild.currentResult,
    title: "${env.JOB_NAME}: ${currentBuild.displayName} ${result}",
    webhookURL: "$env.DISCORD_WEBHOOK"
}