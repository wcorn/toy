package ds.project.toy.global.util;

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
        MockMultipartFile mockMultipartFile = new MockMultipartFile("image", "test.jpg",
            MediaType.IMAGE_JPEG_VALUE, "hello.jpg".getBytes());
        //when
        boolean result = fileUtil.isImageFile(mockMultipartFile);
        //then
        assertThat(result).isTrue();
    }

    @DisplayName(value = "이미지 파일의 속성이 png인지 판별한다.")
    @Test
    void isImageFileWithPNG() {
        //given
        MockMultipartFile mockMultipartFile = new MockMultipartFile("image", "test.png",
            MediaType.IMAGE_PNG_VALUE, "hello.jpg".getBytes());
        //when
        boolean result = fileUtil.isImageFile(mockMultipartFile);
        //then
        assertThat(result).isTrue();
    }

    @DisplayName(value = "파일의 확장자가 없을 경우 false를 반환한다.")
    @Test
    void isImageFileWithoutExtension() {
        //given
        MockMultipartFile mockMultipartFile = new MockMultipartFile("image", "test",
            MediaType.IMAGE_PNG_VALUE, "hello".getBytes());
        //when
        boolean result = fileUtil.isImageFile(mockMultipartFile);
        //then
        assertThat(result).isFalse();
    }

    @DisplayName(value = "파일의 content type이 없을 경우 false를 반환한다.")
    @Test
    void isImageFileWithoutContentType() {
        //given
        MockMultipartFile mockMultipartFile = new MockMultipartFile("image", "test.png",
            null, "test.png".getBytes());
        //when
        boolean result = fileUtil.isImageFile(mockMultipartFile);
        //then
        assertThat(result).isFalse();
    }
    @DisplayName(value = "파일의 content type이 허용된 type이 아닐 경우 false를 반환한다.")
    @Test
    void isImageFileWithOtherContentType() {
        //given
        MockMultipartFile mockMultipartFile = new MockMultipartFile("image", "test.png",
            MediaType.TEXT_PLAIN_VALUE, "test.png".getBytes());
        //when
        boolean result = fileUtil.isImageFile(mockMultipartFile);
        //then
        assertThat(result).isFalse();
    }
}