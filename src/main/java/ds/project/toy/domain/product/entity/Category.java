package ds.project.toy.domain.product.entity;

import ds.project.toy.domain.product.vo.CategoryState;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;
    @Column(name = "content")
    private String content;
    @Column(name = "state")
    private CategoryState categoryState;

    @Builder
    private Category(Long categoryId, String content, CategoryState categoryState) {
        this.categoryId = categoryId;
        this.content = content;
        this.categoryState = categoryState;
    }

    public static Category of(String content, CategoryState categoryState) {
        return Category.builder()
            .content(content)
            .categoryState(categoryState)
            .build();
    }
}
