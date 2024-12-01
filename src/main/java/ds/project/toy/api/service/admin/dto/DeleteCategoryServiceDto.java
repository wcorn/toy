package ds.project.toy.api.service.admin.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DeleteCategoryServiceDto {

    private Long categoryId;

    @Builder
    private DeleteCategoryServiceDto(Long categoryId) {
        this.categoryId = categoryId;
    }

    public static DeleteCategoryServiceDto of(Long categoryId) {
        return DeleteCategoryServiceDto.builder()
            .categoryId(categoryId)
            .build();
    }
}
