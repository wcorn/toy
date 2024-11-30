package ds.project.toy.api.controller.admin.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostCategoryResponse {

    private Long categoryId;

    @Builder
    private PostCategoryResponse(Long categoryId) {
        this.categoryId = categoryId;
    }

    public static PostCategoryResponse of(Long categoryId) {
        return PostCategoryResponse.builder()
            .categoryId(categoryId)
            .build();
    }
}
