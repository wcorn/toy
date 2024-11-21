package ds.project.toy.domain.product.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductState {
    ACTIVE("활동"),
    BAN("정지");
    private final String name;
}