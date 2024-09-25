package ds.project.toy.domain.user.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SocialProviderState {
    ACTIVE("활성화"),
    INACTIVE("비활성화");
    private final String name;
}
