package ds.project.toy.domain.user.repository;

import static ds.project.toy.fixture.user.SocialLoginFixture.createSocialLogin;
import static ds.project.toy.fixture.user.SocialProviderFixture.createSocialProvider;
import static ds.project.toy.fixture.user.UserInfoFixture.createUserInfo;
import static org.assertj.core.api.Assertions.assertThat;

import ds.project.toy.IntegrationTestSupport;
import ds.project.toy.domain.user.entity.SocialLogin;
import ds.project.toy.domain.user.entity.SocialProvider;
import ds.project.toy.domain.user.entity.UserInfo;
import ds.project.toy.domain.user.vo.SocialProviderState;
import ds.project.toy.domain.user.vo.UserInfoRole;
import ds.project.toy.domain.user.vo.UserInfoState;
import ds.project.toy.global.common.vo.OAuth2Provider;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SocialLoginRepositoryTest extends IntegrationTestSupport {

    @DisplayName(value = "소셜 로그인 제공자와 소셜 로그인 id에 따른 소셜 로그인엔티티를 조회한다.")
    @Test
    void findByProviderProviderAndSocialId() {
        //given
        OAuth2Provider provider = OAuth2Provider.KAKAO;
        String socialId = "SocialId";
        SocialProvider socialProvider = socialProviderRepository.save(
            createSocialProvider(provider));
        UserInfo userInfo = createUserInfo();
        socialLoginRepository.save(createSocialLogin(socialProvider, socialId, userInfo));
        //when
        Optional<SocialLogin> socialLogin = socialLoginRepository.findByProviderProviderAndSocialId(
            provider, socialId);
        //then
        assertThat(socialLogin).isPresent();
        assertThat(socialLogin.get().getProvider().getProvider()).isEqualTo(provider);
        assertThat(socialLogin.get().getSocialId()).isEqualTo(socialId);
    }

}