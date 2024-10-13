package ds.project.toy.global.common.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OAuth2Provider {
    KAKAO("kakao", "카카오");

    private final String registrationId;
    private final String name;
}
