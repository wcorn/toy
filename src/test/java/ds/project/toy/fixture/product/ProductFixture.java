package ds.project.toy.fixture.product;

import ds.project.toy.domain.product.entity.Category;
import ds.project.toy.domain.product.entity.Product;
import ds.project.toy.domain.product.vo.ProductState;
import ds.project.toy.domain.product.vo.SellingStatus;
import ds.project.toy.domain.user.entity.UserInfo;

public class ProductFixture {
    public static Product createProduct(UserInfo productOwner, Category category) {
        return Product.of(category, productOwner, "title", "content", 10000L,
            0L, SellingStatus.SELL, ProductState.ACTIVE);
    }
}
