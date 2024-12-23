package ds.project.toy.fixture.file;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

public class MultipartFileFixture {

    public static MockMultipartFile createImageMockMultipartFile(String name) {
        return new MockMultipartFile(name, "test.jpeg", MediaType.IMAGE_JPEG_VALUE,
            "test".getBytes());
    }

    public static MockMultipartFile createImageMockMultipartFile(String name,
        String originalFilename, String mediaType, byte[] bytes) {
        return new MockMultipartFile(name, originalFilename, mediaType, bytes);
    }

    public static MockMultipartFile createImageMockMultipartFile(String name,
        String originalFilename) {
        return new MockMultipartFile(name, originalFilename, MediaType.IMAGE_JPEG_VALUE,
            "test".getBytes());
    }

    public static MockMultipartFile createImageMockMultipartFile(String name, MediaType mediaType) {
        return new MockMultipartFile(name, "test.jpeg", mediaType.toString(),
            "test".getBytes());
    }

    public static MockMultipartFile createJsonMockMultipartFile(String name, byte[] bytes) {
        return new MockMultipartFile(name, "data", MediaType.APPLICATION_JSON_VALUE, bytes);
    }
}
