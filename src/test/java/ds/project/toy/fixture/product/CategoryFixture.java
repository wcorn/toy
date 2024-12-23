package ds.project.toy.fixture.product;

import ds.project.toy.domain.product.entity.Category;
import ds.project.toy.domain.product.vo.CategoryState;

public class CategoryFixture {

    public static Category createCategory() {
        return Category.of("전자기기", CategoryState.ACTIVE);
    }

    public static Category createCategory(String content) {
        return Category.of(content, CategoryState.ACTIVE);
    }

    public static Category createCategory(String content, CategoryState state) {
        return Category.of(content, state);
    }
}
