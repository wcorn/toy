package ds.project.toy.api.controller.admin.dto.request;

import ds.project.toy.api.service.admin.dto.PostCategoryServiceDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostCategoryRequest {
    @Schema(description = "카테고리 이름", example = "전자기기")
    @NotBlank(message = "카테고리는 이름을 입력해주세요")
    private String content;

    @Builder
    private PostCategoryRequest(String content) {
        this.content = content;
    }

    public static PostCategoryRequest of(String content) {
        return PostCategoryRequest.builder()
            .content(content)
            .build();
    }

    public PostCategoryServiceDto toService() {
        return PostCategoryServiceDto.of(content);
    }
}
