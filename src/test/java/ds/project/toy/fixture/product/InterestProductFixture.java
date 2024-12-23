package ds.project.toy.fixture.product;

import ds.project.toy.domain.product.entity.InterestProduct;
import ds.project.toy.domain.product.entity.Product;
import ds.project.toy.domain.user.entity.UserInfo;

public class InterestProductFixture {

    public static InterestProduct createInterestProduct(UserInfo productViewer, Product product) {
        return InterestProduct.of(product, productViewer);
    }
}
