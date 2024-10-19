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

    @DisplayName("엑세스 토큰과 리프레시 토큰을 생성한다.")
    @Test
    void createToken() {
        //given
        String id = "id";
        Duration expectedDuration = Duration.ofDays(14); // refreshTokenValidityInDay는 해당 값에 맞춰 설정

        //when
        AuthToken authToken = jwtTokenProvider.createToken(id);

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
}