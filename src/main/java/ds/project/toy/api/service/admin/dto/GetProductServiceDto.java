package ds.project.toy.api.service.admin.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GetProductServiceDto {

    private Long productId;
    private Long userId;

    @Builder
    private GetProductServiceDto(Long productId, Long userId) {
        this.productId = productId;
        this.userId = userId;
    }

    public static GetProductServiceDto of(Long productId, Long userId) {
        return GetProductServiceDto.builder()
            .productId(productId)
            .userId(userId)
            .build();
    }
}
