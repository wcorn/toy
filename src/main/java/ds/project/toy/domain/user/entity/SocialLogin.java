package ds.project.toy.domain.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "social_login")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SocialLogin {

    @Id
    @Column(name = "user_id")
    private Long userId;
    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private UserInfo userInfo;
    @Column(name = "social_id")
    private String socialId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider")
    private SocialProvider provider;

    @Builder
    private SocialLogin(Long userId, UserInfo userInfo, String socialId, SocialProvider provider) {
        this.userId = userId;
        this.userInfo = userInfo;
        this.socialId = socialId;
        this.provider = provider;
    }

    public static SocialLogin create(UserInfo userInfo, SocialProvider socialProvider,
        String socialId) {
        return SocialLogin.builder()
            .provider(socialProvider)
            .userInfo(userInfo)
            .socialId(socialId)
            .build();
    }

    public static SocialLogin of(SocialProvider provider, String id, UserInfo userInfo) {
        return SocialLogin.builder()
            .provider(provider)
            .userInfo(userInfo)
            .socialId(id)
            .build();
    }
}
