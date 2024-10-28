package ds.project.toy.api.service.auth;

import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;

import ds.project.toy.IntegrationTestSupport;
import ds.project.toy.api.service.auth.dto.ReissuedTokenServiceDto;
import ds.project.toy.domain.user.entity.UserInfo;
import ds.project.toy.domain.user.vo.UserInfoRole;
import ds.project.toy.domain.user.vo.UserInfoState;
import ds.project.toy.global.common.exception.CustomException;
import ds.project.toy.global.common.exception.ResponseCode;
import ds.project.toy.global.common.vo.AuthToken;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AuthServiceTest extends IntegrationTestSupport {

    @DisplayName(value = "토큰을 받아 기존 토큰을 삭제한 후 재생성한다.")
    @Test
    public void reissuedToken() {
        //given
        UserInfo userInfo = userInfoRepository.save(
            createUserInfo("nickname", "email", UserInfoRole.ROLE_USER, UserInfoState.ACTIVE));
        AuthToken authToken = createToken(userInfo.getUserId());
        ReissuedTokenServiceDto dto = ReissuedTokenServiceDto.of(authToken.getAccessToken(),
            authToken.getRefreshToken());
        given(redisUtil.hasKey(any(), any())).willReturn(true);
        //when
        AuthToken response = authService.reissuedToken(dto);
        //then
        assertThat(response).isNotNull();
        assertThat(
            jwtTokenProvider.getUserIdFromToken(authToken.getAccessToken())).isEqualTo(
            jwtTokenProvider.getAuthentication(response.getAccessToken()).getName());
    }

    @DisplayName(value = "redis에 토큰이 안되어 있다면 토큰 재생성 시 예외가 발생한다.")
    @Test
    public void reissuedTokenWithRedisNotStoredToken() {
        //given
        AuthToken authToken = createToken(1);
        ReissuedTokenServiceDto dto = ReissuedTokenServiceDto.of(authToken.getAccessToken(),
            authToken.getRefreshToken());
        given(redisUtil.hasKey(any(), any())).willReturn(false);
        //when
        assertThatThrownBy(() -> authService.reissuedToken(dto))
            .isInstanceOf(CustomException.class)
            .extracting("responseCode")
            .isEqualTo(ResponseCode.INVALID_TOKEN);
        //then

    }

    private AuthToken createToken(long id) {
        return jwtTokenProvider.createTokenAndStore(String.valueOf(id));
    }

    private UserInfo createUserInfo(String nickname, String email,
        UserInfoRole role, UserInfoState state) {
        return UserInfo.of(nickname, email, "image.jpg",role, state);
    }
}