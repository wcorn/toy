package ds.project.toy.domain.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "product_image")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_image_id")
    private Long productImageId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
    @Column(name = "image_url")
    private String imageUrl;

    @Builder
    private ProductImage(Long productImageId, Product product, String imageUrl) {
        this.productImageId = productImageId;
        this.product = product;
        this.imageUrl = imageUrl;
    }

    public static ProductImage of(Product product, String imageUrl) {
        return ProductImage.builder()
            .product(product)
            .imageUrl(imageUrl)
            .build();
    }
}
