package ds.project.toy.api.service.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import ds.project.toy.IntegrationTestSupport;
import ds.project.toy.api.controller.user.dto.response.ChangeNicknameResponse;
import ds.project.toy.api.service.user.dto.ChangeNicknameServiceDto;
import ds.project.toy.domain.user.entity.SocialLogin;
import ds.project.toy.domain.user.entity.SocialProvider;
import ds.project.toy.domain.user.entity.UserInfo;
import ds.project.toy.domain.user.vo.SocialProviderState;
import ds.project.toy.domain.user.vo.UserInfoRole;
import ds.project.toy.domain.user.vo.UserInfoState;
import ds.project.toy.global.common.exception.CustomException;
import ds.project.toy.global.common.exception.ResponseCode;
import ds.project.toy.global.common.vo.OAuth2Provider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserServiceTest extends IntegrationTestSupport {

    @DisplayName(value = "변경할 닉네임을 받아 기존 닉네임을 변경한다.")
    @Test
    public void changeNickname() {
        //given
        String prevNickname = "prev";
        String nextNickname = "nickname";
        SocialProvider socialProvider = socialProviderRepository.save(
            createSocialProvider(OAuth2Provider.KAKAO, SocialProviderState.ACTIVE));
        UserInfo userInfo = createUserInfo(prevNickname, "email",
            UserInfoRole.ROLE_USER, UserInfoState.ACTIVE);
        socialLoginRepository.save(createSocialLogin(socialProvider, "id", userInfo));

        ChangeNicknameServiceDto dto = ChangeNicknameServiceDto.of(
            nextNickname, userInfo.getUserId());
        //when
        ChangeNicknameResponse response = userService.changeNickname(dto);
        //then
        assertThat(response.getNickname()).isEqualTo(nextNickname);
        UserInfo result = userInfoRepository.findById(userInfo.getUserId()).orElseThrow();
        assertThat(result.getNickname()).isEqualTo(nextNickname);
    }

    @DisplayName(value = "닉네임을 변경할 때 기존닉네임일 경우 예외가 발생한다.")
    @Test
    public void changeNicknameWithNicknameAlreadyInUse() {
        //given
        String nickname = "nickname";
        SocialProvider socialProvider = socialProviderRepository.save(
            createSocialProvider(OAuth2Provider.KAKAO, SocialProviderState.ACTIVE));
        UserInfo userInfo = createUserInfo(nickname, "email",
            UserInfoRole.ROLE_USER, UserInfoState.ACTIVE);
        socialLoginRepository.save(createSocialLogin(socialProvider, "id", userInfo));

        ChangeNicknameServiceDto dto = ChangeNicknameServiceDto.of(nickname, userInfo.getUserId());

        //when then
        assertThatThrownBy(() -> userService.changeNickname(dto))
            .isInstanceOf(CustomException.class)
            .extracting("responseCode")
            .isEqualTo(ResponseCode.NICKNAME_ALREADY_IN_USE);
    }

    @DisplayName(value = "닉네임을 변경할 때 중복된 닉네임일 경우 예외가 발생한다.")
    @Test
    public void changeNicknameWithDuplicateNickname() {
        //given
        String prevNickname = "prev";
        String duplicateNickname = "nickname";
        SocialProvider socialProvider = socialProviderRepository.save(
            createSocialProvider(OAuth2Provider.KAKAO, SocialProviderState.ACTIVE));
        UserInfo userInfo = createUserInfo(prevNickname, "email",
            UserInfoRole.ROLE_USER, UserInfoState.ACTIVE);
        socialLoginRepository.save(createSocialLogin(socialProvider, "id1", userInfo));
        UserInfo userInfo2 = createUserInfo(duplicateNickname, "email",
            UserInfoRole.ROLE_USER, UserInfoState.ACTIVE);
        socialLoginRepository.save(createSocialLogin(socialProvider, "id2", userInfo2));

        ChangeNicknameServiceDto dto = ChangeNicknameServiceDto.of(duplicateNickname,
            userInfo.getUserId());

        //when then
        assertThatThrownBy(() -> userService.changeNickname(dto))
            .isInstanceOf(CustomException.class)
            .extracting("responseCode")
            .isEqualTo(ResponseCode.DUPLICATE_NICKNAME);
    }

    private UserInfo createUserInfo(String nickname, String email,
        UserInfoRole role, UserInfoState state) {
        return UserInfo.of(nickname, email,"image.jpg", role, state);
    }

    private SocialLogin createSocialLogin(SocialProvider provider, String id, UserInfo userInfo) {
        return SocialLogin.of(provider, id, userInfo);
    }

    private SocialProvider createSocialProvider(OAuth2Provider provider,
        SocialProviderState state) {
        return SocialProvider.of(provider, state);
    }
}