package ds.project.toy.domain.product.entity;

import ds.project.toy.api.service.product.dto.PostProductServiceDto;
import ds.project.toy.domain.product.vo.ProductState;
import ds.project.toy.domain.product.vo.SellingStatus;
import ds.project.toy.domain.user.entity.UserInfo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity(name = "product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserInfo userInfo;
    @Column(name = "title")
    private String title;
    @Column(name = "content")
    private String content;
    @Column(name = "price")
    private Long price;
    @Column(name = "view")
    private Long view;
    @Enumerated(EnumType.STRING)
    @Column(name = "selling_status")
    private SellingStatus sellingStatus;
    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private ProductState productState;
    @Column(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    private Product(Long productId, Category category, UserInfo userInfo, String title,
        String content, Long price, Long view, SellingStatus sellingStatus,
        ProductState productState, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.productId = productId;
        this.category = category;
        this.userInfo = userInfo;
        this.title = title;
        this.content = content;
        this.price = price;
        this.view = view;
        this.sellingStatus = sellingStatus;
        this.productState = productState;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Product of(Category category, UserInfo userInfo, String title,
        String content, Long price, Long view, SellingStatus sellingStatus,
        ProductState productState, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return Product.builder()
            .category(category)
            .userInfo(userInfo)
            .title(title)
            .content(content)
            .price(price)
            .view(view)
            .sellingStatus(sellingStatus)
            .productState(productState)
            .createdAt(createdAt)
            .updatedAt(updatedAt)
            .build();
    }

    public static Product create(PostProductServiceDto dto, UserInfo userInfo, Category category) {
        return Product.builder()
            .userInfo(userInfo)
            .category(category)
            .title(dto.getTitle())
            .content(dto.getContent())
            .price(dto.getPrice())
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .sellingStatus(SellingStatus.SELL)
            .productState(ProductState.ACTIVE)
            .view(0L)
            .build();
    }
}
