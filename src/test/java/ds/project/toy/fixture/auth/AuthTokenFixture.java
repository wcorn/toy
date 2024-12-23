package ds.project.toy.fixture.auth;

import ds.project.toy.global.common.vo.AuthToken;

public class AuthTokenFixture {

    public static AuthToken createAuthToken() {
        return AuthToken.of("accessToken", "refreshToken");
    }
}
