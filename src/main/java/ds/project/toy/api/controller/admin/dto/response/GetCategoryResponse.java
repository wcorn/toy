package ds.project.toy.api.controller.admin.dto.response;

import ds.project.toy.domain.product.entity.Category;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GetCategoryResponse {

    private Long categoryId;
    private String categoryName;
    private String categoryState;

    @Builder
    private GetCategoryResponse(Long categoryId, String categoryName, String categoryState) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryState = categoryState;
    }

    public static GetCategoryResponse of(Long categoryId, String categoryName, String categoryState) {
        return GetCategoryResponse.builder()
            .categoryId(categoryId)
            .categoryName(categoryName)
            .categoryState(categoryState)
            .build();
    }

    public static GetCategoryResponse fromCategory(Category category) {
        return GetCategoryResponse.builder()
            .categoryId(category.getCategoryId())
            .categoryName(category.getContent())
            .categoryState(category.getCategoryState().getName())
            .build();
    }
}
