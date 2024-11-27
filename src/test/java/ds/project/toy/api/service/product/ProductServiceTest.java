package ds.project.toy.api.service.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import ds.project.toy.IntegrationTestSupport;
import ds.project.toy.api.controller.product.dto.response.PostProductResponse;
import ds.project.toy.api.service.product.dto.PostProductServiceDto;
import ds.project.toy.domain.product.entity.Category;
import ds.project.toy.domain.product.entity.Product;
import ds.project.toy.domain.product.vo.CategoryState;
import ds.project.toy.domain.product.vo.ProductState;
import ds.project.toy.domain.product.vo.SellingStatus;
import ds.project.toy.domain.user.entity.UserInfo;
import ds.project.toy.domain.user.vo.UserInfoRole;
import ds.project.toy.domain.user.vo.UserInfoState;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

class ProductServiceTest extends IntegrationTestSupport {

    @DisplayName(value = "판매할 제품을 등록한다.")
    @Test
    void postProduct() {
        //given
        UserInfo userInfo = userInfoRepository.save(createUser());
        Category category = categoryRepository.save(createCategory("전자기가"));
        String title = "title";
        String content = "content";
        Long price = 10000L;
        MockMultipartFile image = new MockMultipartFile("image", "test2.jpeg",
            MediaType.IMAGE_JPEG_VALUE, "test".getBytes());
        List<MultipartFile> files = List.of(image);
        PostProductServiceDto dto = PostProductServiceDto.of(userInfo.getUserId(), title, content,
            price, category.getCategoryId(), files);
        //when
        PostProductResponse response = productService.postProduct(dto);
        //then
        Optional<Product> product = productRepository.findById(response.getProductId());
        assertThat(product).isPresent();
        assertThat(response.getProductId()).isEqualTo(product.get().getProductId());
        assertThat(product.get().getSellingStatus()).isEqualTo(SellingStatus.SELL);
        assertThat(product.get().getProductState()).isEqualTo(ProductState.ACTIVE);
        assertThat(product.get().getView()).isZero();
        verify(minioUtil, times(files.size())).uploadFile(image);
    }

    private Category createCategory(String content) {
        return Category.of(content, CategoryState.ACTIVE);
    }

    private UserInfo createUser() {
        return UserInfo.of("nickname", "email@gmail.com",
            UserInfoRole.ROLE_USER, UserInfoState.ACTIVE);
    }
}