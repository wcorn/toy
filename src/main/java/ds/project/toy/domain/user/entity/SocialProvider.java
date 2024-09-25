package ds.project.toy.domain.user.entity;

import ds.project.toy.domain.user.vo.Provider;
import ds.project.toy.domain.user.vo.SocialProviderState;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
    private Provider provider;
    @Column(name = "state")
    private SocialProviderState state;

    @Builder
    private SocialProvider(Provider provider, SocialProviderState state) {
        this.provider = provider;
        this.state = state;
    }
}
