package ds.project.toy.domain.product.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CategoryState {
    ACTIVE("활동"),
    INACTIVE("비활동");
    private final String name;
}
