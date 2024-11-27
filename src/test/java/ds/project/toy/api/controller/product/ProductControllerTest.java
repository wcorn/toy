package ds.project.toy.api.controller.product;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ds.project.toy.ControllerTestSupport;
import ds.project.toy.api.controller.product.dto.request.PostProductRequest;
import ds.project.toy.api.controller.product.dto.response.PostProductResponse;
import ds.project.toy.global.common.exception.ResponseCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;

class ProductControllerTest extends ControllerTestSupport {

    @DisplayName(value = "판매할 제품을 등록한다.")
    @WithMockUser(roles = "USER", username = "1")
    @Test
    void postProduct() throws Exception {
        //given
        PostProductRequest request = PostProductRequest.of("data", "content", 10000L, 1L);
        PostProductResponse response = PostProductResponse.of(1L);
        MockMultipartFile data = new MockMultipartFile("data", "test.jpeg",
            MediaType.APPLICATION_JSON_VALUE, objectMapper.writeValueAsString(request).getBytes());
        MockMultipartFile image1 = new MockMultipartFile("image", "test1.jpeg",
            MediaType.IMAGE_JPEG_VALUE, "test".getBytes());
        MockMultipartFile image2 = new MockMultipartFile("image", "test2.jpeg",
            MediaType.IMAGE_JPEG_VALUE, "test".getBytes());
        given(fileUtil.isImageFile(any())).willReturn(true);
        given(productService.postProduct(any())).willReturn(response);
        //when
        mockMvc.perform(
                multipart(HttpMethod.POST, "/product")
                    .file(data)
                    .file(image1)
                    .file(image2)
                    .with(csrf())
            )
            .andExpect(status().isOk())
            .andExpectAll(
                jsonPath("$.productId").value(response.getProductId())
            );
        //then
    }

    @DisplayName(value = "판매할 제품을 등록할 때 이미지가 없어도 된다.")
    @WithMockUser(roles = "USER", username = "1")
    @Test
    void postProductOverThan10Image() throws Exception {
        //given
        PostProductRequest request = PostProductRequest.of("data", "content", 10000L, 1L);
        PostProductResponse response = PostProductResponse.of(1L);
        MockMultipartFile data = new MockMultipartFile("data", "test.jpeg",
            MediaType.APPLICATION_JSON_VALUE, objectMapper.writeValueAsString(request).getBytes());
        MockMultipartFile image1 = new MockMultipartFile("image", "test1.jpeg",
            MediaType.IMAGE_JPEG_VALUE, "test".getBytes());
        MockMultipartFile image2 = new MockMultipartFile("image", "test2.jpeg",
            MediaType.IMAGE_JPEG_VALUE, "test".getBytes());
        MockMultipartFile image3 = new MockMultipartFile("image", "test1.jpeg",
            MediaType.IMAGE_JPEG_VALUE, "test".getBytes());
        MockMultipartFile image4 = new MockMultipartFile("image", "test2.jpeg",
            MediaType.IMAGE_JPEG_VALUE, "test".getBytes());
        MockMultipartFile image5 = new MockMultipartFile("image", "test1.jpeg",
            MediaType.IMAGE_JPEG_VALUE, "test".getBytes());
        MockMultipartFile image6 = new MockMultipartFile("image", "test2.jpeg",
            MediaType.IMAGE_JPEG_VALUE, "test".getBytes());
        MockMultipartFile image7 = new MockMultipartFile("image", "test1.jpeg",
            MediaType.IMAGE_JPEG_VALUE, "test".getBytes());
        MockMultipartFile image8 = new MockMultipartFile("image", "test2.jpeg",
            MediaType.IMAGE_JPEG_VALUE, "test".getBytes());
        MockMultipartFile image9 = new MockMultipartFile("image", "test1.jpeg",
            MediaType.IMAGE_JPEG_VALUE, "test".getBytes());
        MockMultipartFile image10 = new MockMultipartFile("image", "test2.jpeg",
            MediaType.IMAGE_JPEG_VALUE, "test".getBytes());
        MockMultipartFile image11 = new MockMultipartFile("image", "test1.jpeg",
            MediaType.IMAGE_JPEG_VALUE, "test".getBytes());

        given(fileUtil.isImageFile(any())).willReturn(true);
        given(productService.postProduct(any())).willReturn(response);
        given(productService.postProduct(any())).willReturn(response);
        //when
        mockMvc.perform(
                multipart(HttpMethod.POST, "/product")
                    .file(data)
                    .file(image1)
                    .file(image2)
                    .file(image3)
                    .file(image4)
                    .file(image5)
                    .file(image6)
                    .file(image7)
                    .file(image8)
                    .file(image9)
                    .file(image10)
                    .file(image11)
                    .with(csrf())
            )
            .andExpect(status().isBadRequest())
            .andExpectAll(
                jsonPath("$.code").value(ResponseCode.MAX_IMAGE_COUNT_EXCEEDED.getCode()),
                jsonPath("$.message").value(ResponseCode.MAX_IMAGE_COUNT_EXCEEDED.getMessage())
            );
        //then
    }

    @DisplayName(value = "판매할 제품을 등록할 때 이미지가 10장 초과할 경우 예외가 발생한다.")
    @WithMockUser(roles = "USER", username = "1")
    @Test
    void postProductWithEmptyImage() throws Exception {
        //given
        PostProductRequest request = PostProductRequest.of("data", "content", 10000L, 1L);
        PostProductResponse response = PostProductResponse.of(1L);
        MockMultipartFile data = new MockMultipartFile("data", "test.jpeg",
            MediaType.APPLICATION_JSON_VALUE, objectMapper.writeValueAsString(request).getBytes());
        given(productService.postProduct(any())).willReturn(response);
        //when
        mockMvc.perform(
                multipart(HttpMethod.POST, "/product")
                    .file(data)
                    .with(csrf())
            )
            .andExpect(status().isOk())
            .andExpectAll(
                jsonPath("$.productId").value(response.getProductId())
            );
        //then
    }

    @DisplayName(value = "판매할 제품을 등록할때 유효한 제품 이미지가 아닐 경우 예외가 발생한다.")
    @WithMockUser(roles = "USER", username = "1")
    @Test
    void postProductWithInvalidImage() throws Exception {
        //given
        PostProductRequest request = PostProductRequest.of("data", "content", 10000L, 1L);
        PostProductResponse response = PostProductResponse.of(1L);
        MockMultipartFile data = new MockMultipartFile("data", "test.jpeg",
            MediaType.APPLICATION_JSON_VALUE, objectMapper.writeValueAsString(request).getBytes());
        MockMultipartFile image1 = new MockMultipartFile("image", "test1.pdf",
            MediaType.IMAGE_JPEG_VALUE, "test".getBytes());
        MockMultipartFile image2 = new MockMultipartFile("image", "test2.pdf",
            MediaType.IMAGE_JPEG_VALUE, "test".getBytes());
        given(fileUtil.isImageFile(any())).willReturn(false);
        given(productService.postProduct(any())).willReturn(response);
        //when
        mockMvc.perform(
                multipart(HttpMethod.POST, "/product")
                    .file(data)
                    .file(image1)
                    .file(image2)
                    .with(csrf())
            )
            .andExpect(status().isBadRequest())
            .andExpectAll(
                jsonPath("$.code").value(ResponseCode.NOT_IMAGE.getCode()),
                jsonPath("$.message").value(ResponseCode.NOT_IMAGE.getMessage())
            );
        //then
    }
}