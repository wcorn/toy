package ds.project.toy.api.controller.product.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostProductResponse {

    private Long productId;

    @Builder
    public PostProductResponse(Long productId) {
        this.productId = productId;
    }

    public static PostProductResponse of(Long productId) {
        return PostProductResponse.builder()
            .productId(productId)
            .build();
    }
}
