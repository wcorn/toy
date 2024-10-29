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
    public void isImageFileWithJPG() {
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
    public void isImageFileWithPNG() throws Exception {
        //given
        MockMultipartFile mockMultipartFile = new MockMultipartFile("image", "test.png",
            MediaType.IMAGE_PNG_VALUE, "hello.jpg".getBytes());
        //when
        boolean result = fileUtil.isImageFile(mockMultipartFile);
        //then
        assertThat(result).isTrue();
    }
}