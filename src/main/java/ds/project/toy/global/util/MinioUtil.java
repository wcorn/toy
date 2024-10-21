package ds.project.toy.global.util;

import ds.project.toy.global.common.exception.CustomException;
import ds.project.toy.global.common.exception.ResponseCode;
import io.minio.*;
import io.minio.errors.*;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Component
@Slf4j
public class MinioUtil {

    @Value("${minio.bucket.name}")
    private String bucketName;

    private final MinioClient minioClient;

    /**
     * 파일을 Minio에 업로드합니다.
     */
    public void uploadFile(MultipartFile multipartFile) {
        String fileName = generateFileName(multipartFile);
        PutObjectArgs putObjectArgs = createPutObjectArgs(multipartFile, fileName);
        execute(() -> minioClient.putObject(putObjectArgs));
    }

    /**
     * Minio에서 파일을 삭제합니다.
     */
    public void deleteFile(String imageUrl) {
        RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder()
            .bucket(bucketName)
            .object(imageUrl)
            .build();
        execute(() -> minioClient.removeObject(removeObjectArgs));
    }

    /**
     * 파일 이름을 UUID 기반으로 생성합니다.
     */
    private String generateFileName(MultipartFile multipartFile) {
        String extension = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
        return UUID.randomUUID() + (extension != null ? "." + extension : "");
    }

    /**
     * PutObjectArgs 객체를 생성합니다.
     */
    private PutObjectArgs createPutObjectArgs(MultipartFile multipartFile, String fileName) {
        try {
            return PutObjectArgs.builder()
                .bucket(bucketName)
                .object(fileName)
                .stream(multipartFile.getInputStream(), multipartFile.getSize(), -1)
                .contentType(multipartFile.getContentType())
                .build();
        } catch (IOException e) {
            throw new CustomException(ResponseCode.BAD_REQUEST);
        }
    }

    /**
     * 공통 예외 처리를 위한 메서드입니다.
     */
    private void execute(ThrowingRunnable action) {
        try {
            action.run();
        } catch (ServerException | InternalException | ErrorResponseException e) {
            log.error("{}", e.getMessage(), e);
            throw new CustomException(
                ResponseCode.INTERNAL_SERVER_ERROR);
        } catch (InsufficientDataException | InvalidResponseException | XmlParserException |
                 NoSuchAlgorithmException | InvalidKeyException | IOException e) {
            log.error("{}", e.getMessage(), e);
            throw new CustomException(ResponseCode.BAD_REQUEST);
        }
    }

    @FunctionalInterface
    private interface ThrowingRunnable {

        void run() throws ServerException, InsufficientDataException, ErrorResponseException,
            IOException, NoSuchAlgorithmException, InvalidKeyException,
            InvalidResponseException, XmlParserException, InternalException;
    }
}
