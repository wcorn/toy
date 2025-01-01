package ds.project.toy.api.controller.product.dto.response;

import ds.project.toy.domain.product.entity.Category;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GetCategoryResponse {

    private Long categoryId;
    private String categoryName;

    @Builder
    private GetCategoryResponse(Long categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public static GetCategoryResponse of(Long categoryId, String categoryName) {
        return GetCategoryResponse.builder()
            .categoryId(categoryId)
            .categoryName(categoryName)
            .build();
    }

    public static GetCategoryResponse fromCategory(Category category) {
        return GetCategoryResponse.builder()
            .categoryId(category.getCategoryId())
            .categoryName(category.getContent())
            .build();
    }
}
