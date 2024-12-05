package ds.project.toy.domain.product.repository;

import static org.assertj.core.api.Assertions.assertThat;

import ds.project.toy.IntegrationTestSupport;
import ds.project.toy.domain.product.entity.Category;
import ds.project.toy.domain.product.entity.InterestProduct;
import ds.project.toy.domain.product.entity.Product;
import ds.project.toy.domain.product.vo.CategoryState;
import ds.project.toy.domain.product.vo.ProductState;
import ds.project.toy.domain.product.vo.SellingStatus;
import ds.project.toy.domain.user.entity.UserInfo;
import ds.project.toy.domain.user.vo.UserInfoRole;
import ds.project.toy.domain.user.vo.UserInfoState;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InterestProductRepositoryTest extends IntegrationTestSupport {

    @DisplayName(value = "관심 제품이 있는지 확인한다.")
    @Test
    void existsByUserInfoAndProduct() {
        //given
        UserInfo userInfo = userInfoRepository.save(createUser());
        Category category = categoryRepository.save(createCategory("전자기가"));
        Product product = productRepository.save(createProduct(userInfo, category));
        interestProductRepository.save(
            createInterestProduct(userInfo, product));
        //when
        boolean exists = interestProductRepository.existsByUserInfoAndProduct(userInfo, product);
        //then
        assertThat(exists).isTrue();
    }

    private InterestProduct createInterestProduct(UserInfo userInfo, Product product) {
        return InterestProduct.of(product, userInfo);
    }

    private Product createProduct(UserInfo productOwner, Category category) {
        return Product.of(category, productOwner, "제목", "내용", 10000L,
            0L, SellingStatus.SELL, ProductState.ACTIVE);
    }

    private Category createCategory(String content) {
        return Category.of(content, CategoryState.ACTIVE);
    }

    private UserInfo createUser() {
        return UserInfo.of("nickname", "email@gmail.com",
            UserInfoRole.ROLE_USER, UserInfoState.ACTIVE);
    }
}