package ds.project.toy.api.controller.product.dto.response;

import ds.project.toy.domain.product.entity.Product;
import ds.project.toy.domain.product.entity.ProductImage;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GetProductResponse {

    Long userId;
    String userNickname;
    String userProfileImageUrl;

    Long productId;
    String productTitle;
    String productContent;
    Long productPrice;
    Integer productInterestCount;
    Long productView;
    List<String> productImageUrls;
    String productDateTime;
    boolean interested;

    @Builder
    private GetProductResponse(Long userId, String userNickname, String userProfileImageUrl,
        Long productId, String productTitle, String productContent, Long productPrice,
        Integer productInterestCount, Long productView, List<String> productImageUrls,
        String productDateTime, boolean interested) {
        this.userId = userId;
        this.userNickname = userNickname;
        this.userProfileImageUrl = userProfileImageUrl;
        this.productId = productId;
        this.productTitle = productTitle;
        this.productContent = productContent;
        this.productPrice = productPrice;
        this.productInterestCount = productInterestCount;
        this.productView = productView;
        this.productImageUrls = productImageUrls;
        this.productDateTime = productDateTime;
        this.interested = interested;
    }

    public static GetProductResponse of(Product product, boolean interested) {
        return GetProductResponse.builder()
            .userId(product.getUserInfo().getUserId())
            .userNickname(product.getUserInfo().getNickname())
            .userProfileImageUrl(product.getUserInfo().getProfileImage())
            .productId(product.getProductId())
            .productTitle(product.getTitle())
            .productContent(product.getContent())
            .productPrice(product.getPrice())
            .productInterestCount(product.getInterestProducts().size())
            .productView(product.getView())
            .productImageUrls(product.getProductImages().stream().map(ProductImage::getImageUrl).toList())
            .productDateTime(product.getUpdatedAt().toString())
            .interested(interested)
            .build();
    }
    public static GetProductResponse of(Long userId, String userNickname, String userProfileImageUrl,
        Long productId, String productTitle, String productContent, Long productPrice,
        Integer productInterestCount, Long productView, List<String> productImageUrls,
        String productDateTime, boolean interested) {
        return GetProductResponse.builder()
            .userId(userId)
            .userNickname(userNickname)
            .userProfileImageUrl(userProfileImageUrl)
            .productId(productId)
            .productTitle(productTitle)
            .productContent(productContent)
            .productPrice(productPrice)
            .productInterestCount(productInterestCount)
            .productView(productView)
            .productImageUrls(productImageUrls)
            .productDateTime(productDateTime)
            .interested(interested)
            .build();
    }
}
