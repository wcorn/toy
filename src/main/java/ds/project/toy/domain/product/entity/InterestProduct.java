package ds.project.toy.domain.product.entity;

import ds.project.toy.domain.user.entity.UserInfo;
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

@Entity(name = "interest_product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
public class InterestProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interest_product_id")
    private Long interestProductId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserInfo userInfo;

    @Builder
    private InterestProduct(Long interestProductId, Product product, UserInfo userInfo) {
        this.interestProductId = interestProductId;
        this.product = product;
        this.userInfo = userInfo;
    }

    public static InterestProduct of(Long interestProductId, Product product, UserInfo userInfo) {
        return InterestProduct.builder()
            .interestProductId(interestProductId)
            .product(product)
            .userInfo(userInfo)
            .build();
    }
}
