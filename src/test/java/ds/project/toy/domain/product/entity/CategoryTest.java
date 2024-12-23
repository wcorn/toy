package ds.project.toy.domain.product.entity;

import static ds.project.toy.fixture.product.CategoryFixture.createCategory;
import static org.assertj.core.api.Assertions.assertThat;

import ds.project.toy.IntegrationTestSupport;
import ds.project.toy.domain.product.vo.CategoryState;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CategoryTest extends IntegrationTestSupport {

    @DisplayName(value = " 엔티티 상태를 삭제로 변경한다.")
    @Test
    void delete() {
        //given
        Category category = createCategory();
        //when
        category.delete();
        //then
        assertThat(category.getCategoryState()).isEqualTo(CategoryState.INACTIVE);
    }

}