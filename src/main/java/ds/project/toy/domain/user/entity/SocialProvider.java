package ds.project.toy.domain.user.entity;

import static jakarta.persistence.EnumType.STRING;

import ds.project.toy.global.common.vo.OAuth2Provider;
import ds.project.toy.domain.user.vo.SocialProviderState;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "social_provider")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SocialProvider {

    @Id
    @Column(name = "provider")
    @Enumerated(STRING)
    private OAuth2Provider provider;
    @Column(name = "state")
    @Enumerated(STRING)
    private SocialProviderState state;

    @Builder
    private SocialProvider(OAuth2Provider oAuth2Provider, SocialProviderState state) {
        this.provider = oAuth2Provider;
        this.state = state;
    }

    public static SocialProvider of(OAuth2Provider provider, SocialProviderState state) {
        return SocialProvider.builder()
            .oAuth2Provider(provider)
            .state(state)
            .build();
    }
}
