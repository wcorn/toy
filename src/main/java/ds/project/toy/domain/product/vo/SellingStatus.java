package ds.project.toy.domain.product.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SellingStatus {
    SELL("판매중"),
    TRADE("거래중"),
    SOLD("거래완료");
    private final String name;
}
