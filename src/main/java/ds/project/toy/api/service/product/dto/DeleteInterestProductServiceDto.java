package ds.project.toy.api.service.product.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DeleteInterestProductServiceDto {

    private Long productId;
    private Long userId;

    @Builder
    private DeleteInterestProductServiceDto(Long productId, Long userId) {
        this.productId = productId;
        this.userId = userId;
    }

    public static DeleteInterestProductServiceDto of(Long productId, Long userId) {
        return DeleteInterestProductServiceDto.builder()
            .productId(productId)
            .userId(userId)
            .build();
    }
}
