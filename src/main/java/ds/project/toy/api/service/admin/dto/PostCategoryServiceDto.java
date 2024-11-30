package ds.project.toy.api.service.admin.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostCategoryServiceDto {

    private String content;

    @Builder
    private PostCategoryServiceDto(String content) {
        this.content = content;
    }

    public static PostCategoryServiceDto of(String content) {
        return PostCategoryServiceDto.builder()
            .content(content)
            .build();
    }
}
