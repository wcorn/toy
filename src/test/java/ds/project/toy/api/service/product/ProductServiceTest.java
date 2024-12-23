package ds.project.toy.api.service.product;

import static ds.project.toy.fixture.file.MultipartFileFixture.createImageMockMultipartFile;
import static ds.project.toy.fixture.product.CategoryFixture.createCategory;
import static ds.project.toy.fixture.product.InterestProductFixture.createInterestProduct;
import static ds.project.toy.fixture.product.ProductFixture.createProduct;
import static ds.project.toy.fixture.user.UserInfoFixture.createUserInfo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import ds.project.toy.IntegrationTestSupport;
import ds.project.toy.api.controller.product.dto.response.GetProductResponse;
import ds.project.toy.api.controller.product.dto.response.PostProductResponse;
import ds.project.toy.api.service.admin.dto.GetProductServiceDto;
import ds.project.toy.api.service.product.dto.DeleteInterestProductServiceDto;
import ds.project.toy.api.service.product.dto.PostInterestProductServiceDto;
import ds.project.toy.api.service.product.dto.PostProductServiceDto;
import ds.project.toy.domain.product.entity.Category;
import ds.project.toy.domain.product.entity.InterestProduct;
import ds.project.toy.domain.product.entity.Product;
import ds.project.toy.domain.product.vo.ProductState;
import ds.project.toy.domain.product.vo.SellingStatus;
import ds.project.toy.domain.user.entity.UserInfo;
import ds.project.toy.global.common.vo.RedisPrefix;
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
        UserInfo userInfo = userInfoRepository.save(createUserInfo());
        Category category = categoryRepository.save(createCategory());
        MockMultipartFile image = createImageMockMultipartFile("image");
        List<MultipartFile> files = List.of(image);
        PostProductServiceDto dto = PostProductServiceDto.of(userInfo.getUserId(), "title",
            "content", 10000L, category.getCategoryId(), files);
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

    @DisplayName(value = "등록된 제품 페이지를 조회한다.")
    @Test
    void getProduct() {
        //given
        UserInfo productOwner = userInfoRepository.save(createUserInfo());
        UserInfo productViewer = userInfoRepository.save(createUserInfo());
        Category category = categoryRepository.save(createCategory());
        Product product = productRepository.save(createProduct(productOwner, category));
        GetProductServiceDto dto = GetProductServiceDto.of(product.getProductId(),
            productViewer.getUserId());
        given(redisUtil.hasKey(RedisPrefix.VIEW,
            String.valueOf(productViewer.getUserId()))).willReturn(false);
        //when
        GetProductResponse response = productService.getProduct(dto);
        //then
        verify(redisUtil, times(1)).setSetWithExpire(any(), any(), any(), any());
        assertThat(response.getProductView()).isEqualTo(1);
    }

    @DisplayName(value = "등록된 제품 페이지를 조회할 때 본인이 조회한 경우 조회수가 오르지 않는다.")
    @Test
    void getProductWithViewerIsProductOwner() {
        //given
        UserInfo productOwner = userInfoRepository.save(createUserInfo());
        Category category = categoryRepository.save(createCategory());
        Product product = productRepository.save(createProduct(productOwner, category));
        GetProductServiceDto dto = GetProductServiceDto.of(product.getProductId(),
            productOwner.getUserId());
        given(redisUtil.hasKey(RedisPrefix.VIEW,
            String.valueOf(productOwner.getUserId()))).willReturn(false);
        //when
        GetProductResponse response = productService.getProduct(dto);
        //then
        assertThat(response.getProductView()).isZero();
    }

    @DisplayName(value = "등록된 제품 페이지를 조회할 때 이미 조회 했을 경우 조회수가 오르지 않는다.")
    @Test
    void getProductAlreadyViewed() {
        //given
        UserInfo productOwner = userInfoRepository.save(createUserInfo());
        UserInfo productViewer = userInfoRepository.save(createUserInfo());
        Category category = categoryRepository.save(createCategory());
        Product product = productRepository.save(createProduct(productOwner, category));
        GetProductServiceDto dto = GetProductServiceDto.of(product.getProductId(),
            productViewer.getUserId());
        given(redisUtil.hasKey(RedisPrefix.VIEW,
            String.valueOf(productViewer.getUserId()))).willReturn(true);
        //when
        GetProductResponse response = productService.getProduct(dto);
        //then
        assertThat(response.getProductView()).isZero();
    }

    @DisplayName(value = "관심물품을 등록한다.")
    @Test
    void postInterestProduct() {
        //given
        UserInfo productViewer = userInfoRepository.save(createUserInfo());
        UserInfo productOwner = userInfoRepository.save(createUserInfo());
        Category category = categoryRepository.save(createCategory());
        Product product = productRepository.save(createProduct(productOwner, category));
        PostInterestProductServiceDto dto = PostInterestProductServiceDto.of(product.getProductId(),
            productViewer.getUserId());
        //when
        productService.postInterestProduct(dto);
        //then
        List<InterestProduct> interestProducts = interestProductRepository.findAll();
        assertThat(interestProducts).hasSize(1);
        assertThat(interestProducts.get(0).getProduct().getProductId()).isEqualTo(
            product.getProductId());
        assertThat(interestProducts.get(0).getUserInfo().getUserId()).isEqualTo(
            productViewer.getUserId());
    }

    @DisplayName(value = "관심 물품을 관심 목록에서 삭제한다.")
    @Test
    void deleteInterestProduct() {
        //given
        UserInfo productViewer = userInfoRepository.save(createUserInfo());
        UserInfo productOwner = userInfoRepository.save(createUserInfo());
        Category category = categoryRepository.save(createCategory());
        Product product = productRepository.save(createProduct(productOwner, category));
        DeleteInterestProductServiceDto dto = DeleteInterestProductServiceDto.of(
            product.getProductId(), productViewer.getUserId());
        interestProductRepository.save(createInterestProduct(productViewer, product));
        //when
        productService.deleteInterestProduct(dto);
        //then
        assertThat(interestProductRepository.findAll()).isEmpty();
    }

}