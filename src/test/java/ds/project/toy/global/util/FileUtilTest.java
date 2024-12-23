package ds.project.toy.global.util;

import static ds.project.toy.fixture.file.MultipartFileFixture.createImageMockMultipartFile;
import static org.assertj.core.api.Assertions.assertThat;

import ds.project.toy.IntegrationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

class FileUtilTest extends IntegrationTestSupport {

    @DisplayName(value = "이미지 파일의 속성이 jpg인지 판별한다.")
    @Test
    void isImageFileWithJPG() {
        //given
        MockMultipartFile mockMultipartFile = createImageMockMultipartFile("image", "image.jpeg",
            MediaType.IMAGE_JPEG_VALUE, "image.jpeg".getBytes());
        //when
        boolean result = fileUtil.isImageFile(mockMultipartFile);
        //then
        assertThat(result).isTrue();
    }

    @DisplayName(value = "이미지 파일의 속성이 png인지 판별한다.")
    @Test
    void isImageFileWithPNG() {
        //given
        MockMultipartFile mockMultipartFile = createImageMockMultipartFile("image", "image.png",
            MediaType.IMAGE_PNG_VALUE, "image.png".getBytes());
        //when
        boolean result = fileUtil.isImageFile(mockMultipartFile);
        //then
        assertThat(result).isTrue();
    }

    @DisplayName(value = "파일의 확장자가 없을 경우 false를 반환한다.")
    @Test
    void isImageFileWithoutExtension() {
        //given
        MockMultipartFile mockMultipartFile = createImageMockMultipartFile("image", "image");
        //when
        boolean result = fileUtil.isImageFile(mockMultipartFile);
        //then
        assertThat(result).isFalse();
    }

    @DisplayName(value = "파일의 content type이 없을 경우 false를 반환한다.")
    @Test
    void isImageFileWithoutContentType() {
        //given
        MockMultipartFile mockMultipartFile = createImageMockMultipartFile("image", "image.png",
            null, "image.png".getBytes());
        //when
        boolean result = fileUtil.isImageFile(mockMultipartFile);
        //then
        assertThat(result).isFalse();
    }

    @DisplayName(value = "파일의 content type이 허용된 type이 아닐 경우 false를 반환한다.")
    @Test
    void isImageFileWithOtherContentType() {
        //given
        MockMultipartFile mockMultipartFile = createImageMockMultipartFile("image",
            MediaType.APPLICATION_JSON);
        //when
        boolean result = fileUtil.isImageFile(mockMultipartFile);
        //then
        assertThat(result).isFalse();
    }
}