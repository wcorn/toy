package ds.project.toy.api.controller.admin.dto.request;

import ds.project.toy.api.service.admin.dto.DeleteCategoryServiceDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.N;

@Getter
@NoArgsConstructor
public class DeleteCategoryRequest {

    private Long categoryId;

    @Builder
    private DeleteCategoryRequest(Long categoryId) {
        this.categoryId = categoryId;
    }

    public static DeleteCategoryRequest of(Long categoryId) {
        return DeleteCategoryRequest.builder()
            .categoryId(categoryId)
            .build();
    }

    public DeleteCategoryServiceDto toService() {
        return DeleteCategoryServiceDto.of(categoryId);
    }
}
