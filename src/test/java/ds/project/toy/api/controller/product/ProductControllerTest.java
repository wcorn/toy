package ds.project.toy.api.controller.product;

import static ds.project.toy.fixture.file.MultipartFileFixture.createImageMockMultipartFile;
import static ds.project.toy.fixture.file.MultipartFileFixture.createJsonMockMultipartFile;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ds.project.toy.ControllerTestSupport;
import ds.project.toy.api.controller.product.dto.request.PostProductRequest;
import ds.project.toy.api.controller.product.dto.response.GetCategoryResponse;
import ds.project.toy.api.controller.product.dto.response.GetProductResponse;
import ds.project.toy.api.controller.product.dto.response.PostProductResponse;
import ds.project.toy.global.common.api.CustomResponseCode;
import ds.project.toy.global.common.exception.ResponseCode;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;

class ProductControllerTest extends ControllerTestSupport {

    @DisplayName(value = "판매할 제품을 등록한다.")
    @WithMockUser(roles = "USER", username = "1")
    @Test
    void postProduct() throws Exception {
        //given
        PostProductRequest request = PostProductRequest.of("data", "content", 10000L, 1L);
        PostProductResponse response = PostProductResponse.of(1L);
        MockMultipartFile data = createJsonMockMultipartFile("data",
            objectMapper.writeValueAsString(request).getBytes());
        MockMultipartFile image1 = createImageMockMultipartFile("image");
        MockMultipartFile image2 = createImageMockMultipartFile("image");
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

    @DisplayName("판매할 제품을 등록할 때 이미지가 10장 초과할 경우 예외가 발생한다.")
    @WithMockUser(roles = "USER", username = "1")
    @Test
    void postProductOverThan10Image() throws Exception {
        // given
        PostProductRequest request = PostProductRequest.of("data", "content", 10000L, 1L);
        MockMultipartFile data = createJsonMockMultipartFile("data", objectMapper.writeValueAsString(request).getBytes());
        List<MockMultipartFile> images = IntStream.range(0, 11) // 11개의 이미지를 생성해 초과 조건을 만족
            .mapToObj(i -> createImageMockMultipartFile("image"))
            .toList();

        given(fileUtil.isImageFile(any())).willReturn(true);

        // when
        MockMultipartHttpServletRequestBuilder requestBuilder = multipart(HttpMethod.POST, "/product");
        requestBuilder.file(data).with(csrf());
        images.forEach(requestBuilder::file);

        // then
        mockMvc.perform(requestBuilder)
            .andExpect(status().isBadRequest())
            .andExpectAll(
                jsonPath("$.code").value(ResponseCode.MAX_IMAGE_COUNT_EXCEEDED.getCode()),
                jsonPath("$.message").value(ResponseCode.MAX_IMAGE_COUNT_EXCEEDED.getMessage())
            );
    }


    @DisplayName(value = "판매할 제품을 등록할 때 이미지가 없어도 된다.")
    @WithMockUser(roles = "USER", username = "1")
    @Test
    void postProductWithEmptyImage() throws Exception {
        //given
        PostProductRequest request = PostProductRequest.of("data", "content", 10000L, 1L);
        PostProductResponse response = PostProductResponse.of(1L);
        MockMultipartFile data = createJsonMockMultipartFile("data",
            objectMapper.writeValueAsString(request).getBytes());
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
        MockMultipartFile data = createJsonMockMultipartFile("data",
            objectMapper.writeValueAsString(request).getBytes());
        MockMultipartFile image1 = createImageMockMultipartFile("image");
        MockMultipartFile image2 = createImageMockMultipartFile("image");
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

    @DisplayName(value = "제품을 조회한다.")
    @WithMockUser(roles = "USER", username = "1")
    @Test
    void getProduct() throws Exception {
        //given
        Long userId = 1L;
        String userNickname = "testUser";
        String userProfileImageUrl = "https://example.com/profile.jpg";
        Long productId = 101L;
        String productTitle = "Sample Product";
        String productContent = "This is a sample product description.";
        Long productPrice = 1999L;
        Integer productInterestCount = 10;
        Long productView = 150L;
        List<String> productImageUrls = Collections.singletonList("https://example.com/image1.jpg");
        String productDateTime = "2024-01-01T10:00:00";
        boolean interested = true;
        GetProductResponse response = GetProductResponse.of(userId, userNickname,
            userProfileImageUrl, productId, productTitle, productContent, productPrice,
            productInterestCount, productView, productImageUrls, productDateTime, interested);
        given(productService.getProduct(any())).willReturn(response);
        //when
        mockMvc.perform(
                get("/product/{productId}", productId)
                    .with(csrf())
            ).andExpect(status().isOk())
            .andExpectAll(
                jsonPath("$.userId").value(userId),
                jsonPath("$.userNickname").value(userNickname),
                jsonPath("$.userProfileImageUrl").value(userProfileImageUrl),
                jsonPath("$.productId").value(productId),
                jsonPath("$.productTitle").value(productTitle),
                jsonPath("$.productContent").value(productContent),
                jsonPath("$.productPrice").value(productPrice.intValue()),
                jsonPath("$.productInterestCount").value(productInterestCount),
                jsonPath("$.productView").value(productView.intValue()),
                jsonPath("$.productImageUrls[0]").value(productImageUrls.get(0)),
                jsonPath("$.productDateTime").value(productDateTime),
                jsonPath("$.interested").value(interested)
            );
        //then
    }

    @DisplayName(value = "관심 물품을 등록한다.")
    @WithMockUser(roles = "USER", username = "1")
    @Test
    void postInterestProduct() throws Exception {
        //given
        Long productId = 1L;
        //when
        mockMvc.perform(
                post("/product/{productId}/interest", productId)
                    .with(csrf())
            ).andExpect(status().isOk())
            .andExpectAll(
                jsonPath("$.message").value(CustomResponseCode.SUCCESS.getMessage())
            );
        //then

    }

    @DisplayName(value = "관심 물품을 관심 목록에서 삭제한다.")
    @WithMockUser(roles = "USER", username = "1")
    @Test
    void deleteInterestProduct() throws Exception {
        //given
        Long productId = 1L;
        //when
        mockMvc.perform(
                delete("/product/{productId}/interest", productId)
                    .with(csrf())
            ).andExpect(status().isOk())
            .andExpectAll(
                jsonPath("$.message").value(CustomResponseCode.SUCCESS.getMessage())
            );
        //then

    }
    @DisplayName(value = "제품 카테고리 목록을 조회한다.")
    @WithMockUser(roles = "USER", username = "1")
    @Test
    void getCategoryList() throws Exception {
        //given
        Long categoryId = 1L;
        String categoryName = "전자기기";
        given(productService.getCategoryList()).willReturn(Collections.singletonList(
            GetCategoryResponse.of(categoryId, categoryName)));
        //when
        mockMvc.perform(
                get("/product/category")
                    .with(csrf())
            ).andExpect(status().isOk())
            .andExpectAll(
                jsonPath("$[0].categoryId").value(categoryId),
                jsonPath("$[0].categoryName").value(categoryName)
            );
        //then

    }
}