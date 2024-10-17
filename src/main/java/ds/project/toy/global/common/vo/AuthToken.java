package ds.project.toy.global.common.vo;

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

}
