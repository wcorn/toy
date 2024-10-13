package ds.project.toy.global.common.vo;

import ds.project.toy.domain.user.vo.UserInfoRole;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class AuthToken {

    private String accessToken;
    private String refreshToken;
    private UserInfoRole userInfoRole;

    @Builder
    public AuthToken(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static AuthToken of(String accessToken, String refreshToken) {
        return AuthToken.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
    }

    public void setRole(UserInfoRole userInfoRole) {
        this.userInfoRole = userInfoRole;
    }
}
