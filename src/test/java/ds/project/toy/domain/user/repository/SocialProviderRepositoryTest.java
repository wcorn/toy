package ds.project.toy.domain.user.repository;

import static ds.project.toy.fixture.user.SocialProviderFixture.createSocialProvider;
import static org.assertj.core.api.Assertions.assertThat;

import ds.project.toy.IntegrationTestSupport;
import ds.project.toy.domain.user.entity.SocialProvider;
import ds.project.toy.domain.user.vo.SocialProviderState;
import ds.project.toy.global.common.vo.OAuth2Provider;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SocialProviderRepositoryTest extends IntegrationTestSupport {

    @DisplayName(value = "소셜로그인 제공자에 따른 활성화된 소셜 로그인 제공자엔티티를 조회한다.")
    @Test
    void findByProviderAndState() {
        //given
        OAuth2Provider provider = OAuth2Provider.KAKAO;
        SocialProviderState state = SocialProviderState.ACTIVE;
        socialProviderRepository.save(createSocialProvider(provider, state));
        //when
        Optional<SocialProvider> socialProvider = socialProviderRepository.findByProviderAndState(
            provider, state);
        //then
        assertThat(socialProvider).isPresent();
        assertThat(socialProvider.get().getProvider()).isEqualTo(provider);
    }

}