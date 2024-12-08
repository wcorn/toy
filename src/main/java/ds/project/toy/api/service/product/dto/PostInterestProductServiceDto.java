package ds.project.toy.api.service.product.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostInterestProductServiceDto {

    private Long productId;
    private Long userId;

    @Builder
    private PostInterestProductServiceDto(Long productId, Long userId) {
        this.productId = productId;
        this.userId = userId;
    }

    public static PostInterestProductServiceDto of(Long productId, Long userId) {
        return PostInterestProductServiceDto.builder()
            .productId(productId)
            .userId(userId)
            .build();
    }
}
