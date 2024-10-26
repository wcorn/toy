package ds.project.toy.global.config.security.jwt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import ds.project.toy.IntegrationTestSupport;
import ds.project.toy.global.common.vo.AuthToken;
import ds.project.toy.global.common.vo.RedisPrefix;
import java.time.Duration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JwtTokenProviderTest extends IntegrationTestSupport {

    @DisplayName("엑세스 토큰과 리프레시 토큰을 생성하고 저장한다.")
    @Test
    void createTokenAndStore() {
        //given
        String id = "id";
        Duration expectedDuration = Duration.ofDays(14); // refreshTokenValidityInDay는 해당 값에 맞춰 설정

        //when
        AuthToken authToken = jwtTokenProvider.createTokenAndStore(id);

        //then
        assertThat(authToken).isNotNull();
        assertThat(authToken.getAccessToken()).isNotNull();
        assertThat(authToken.getRefreshToken()).isNotNull();
        then(redisUtil).should(times(1))
            .setValueWithExpire(
                eq(RedisPrefix.TOKEN),
                eq(authToken.getRefreshToken()),
                eq(authToken.getAccessToken()),
                eq(expectedDuration)
            );
    }

    @DisplayName(value = "리프레시 토큰으로 유저아이디를 조회한다.")
    @Test
    public void getUserIdFromRefreshToken() {
        //given
        String userId = "id";
        AuthToken authToken = jwtTokenProvider.createTokenAndStore(userId);

        //when
        String fromTokenId = jwtTokenProvider.getUserIdFromToken(
            authToken.getRefreshToken());

        //then
        assertThat(fromTokenId).isEqualTo(userId);
    }

}