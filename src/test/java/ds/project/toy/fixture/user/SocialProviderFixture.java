package ds.project.toy.fixture.user;

import ds.project.toy.domain.user.entity.SocialProvider;
import ds.project.toy.domain.user.vo.SocialProviderState;
import ds.project.toy.global.common.vo.OAuth2Provider;

public class SocialProviderFixture {

    public static SocialProvider createSocialProvider() {
        return SocialProvider.of(OAuth2Provider.KAKAO, SocialProviderState.ACTIVE);
    }

    public static SocialProvider createSocialProvider(OAuth2Provider provider,
        SocialProviderState state) {
        return SocialProvider.of(provider, state);
    }

    public static SocialProvider createSocialProvider(OAuth2Provider provider) {
        return SocialProvider.of(provider, SocialProviderState.ACTIVE);
    }
}
