package ds.project.toy.fixture.user;

import ds.project.toy.domain.user.entity.SocialLogin;
import ds.project.toy.domain.user.entity.SocialProvider;
import ds.project.toy.domain.user.entity.UserInfo;

public class SocialLoginFixture {

    public static SocialLogin createSocialLogin(SocialProvider provider, UserInfo userInfo) {
        return SocialLogin.of(provider, "id", userInfo);
    }

    public static SocialLogin createSocialLogin(SocialProvider provider, String socialId,
        UserInfo userInfo) {
        return SocialLogin.of(provider, socialId, userInfo);
    }
}
