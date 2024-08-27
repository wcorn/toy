# Step 1: Build minimal JRE using jlink
FROM amazoncorretto:17-alpine3.20 AS builder-jre

# Step 2: Install binutils (required for copying or converting object files)
RUN apk add --no-cache binutils

# Step 3: Create minimal JRE using jlink
RUN $JAVA_HOME/bin/jlink \
         --module-path "$JAVA_HOME/jmods" \
         --add-modules ALL-MODULE-PATH \
         --strip-debug \
         --no-man-pages \
         --no-header-files \
         --compress=2 \
         --output /jre

# Step 4: Use a new base image for the final stage
FROM alpine:3.20.2

ENV JAVA_HOME=/jre
ENV PATH="$JAVA_HOME/bin:$PATH"

# Step 5: Copy the minimal JRE from the builder stage
COPY --from=builder-jre /jre $JAVA_HOME

WORKDIR /app

# Step 6: Copy the application JAR
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

# Step 7: Run the application
ENTRYPOINT ["sh", "-c", "java -jar -Dspring.profiles.active=dev /app.jar"]