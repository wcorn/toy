package ds.project.toy.domain.product.repository;

import static ds.project.toy.fixture.product.CategoryFixture.createCategory;
import static ds.project.toy.fixture.product.InterestProductFixture.createInterestProduct;
import static ds.project.toy.fixture.product.ProductFixture.createProduct;
import static ds.project.toy.fixture.user.UserInfoFixture.createUserInfo;
import static org.assertj.core.api.Assertions.assertThat;

import ds.project.toy.IntegrationTestSupport;
import ds.project.toy.domain.product.entity.Category;
import ds.project.toy.domain.product.entity.InterestProduct;
import ds.project.toy.domain.product.entity.Product;
import ds.project.toy.domain.product.vo.ProductState;
import ds.project.toy.domain.product.vo.SellingStatus;
import ds.project.toy.domain.user.entity.UserInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InterestProductRepositoryTest extends IntegrationTestSupport {

    @DisplayName(value = "관심 제품이 있는지 확인한다.")
    @Test
    void existsByUserInfoAndProduct() {
        //given
        UserInfo userInfo = userInfoRepository.save(createUserInfo());
        Category category = categoryRepository.save(createCategory());
        Product product = productRepository.save(createProduct(userInfo, category));
        interestProductRepository.save(createInterestProduct(userInfo, product));
        //when
        boolean exists = interestProductRepository.existsByUserInfoAndProduct(userInfo, product);
        //then
        assertThat(exists).isTrue();
    }

}