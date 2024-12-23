package ds.project.toy.api.service.user;

import static ds.project.toy.fixture.file.MultipartFileFixture.createImageMockMultipartFile;
import static ds.project.toy.fixture.user.SocialLoginFixture.createSocialLogin;
import static ds.project.toy.fixture.user.SocialProviderFixture.createSocialProvider;
import static ds.project.toy.fixture.user.UserInfoFixture.createUserInfo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import ds.project.toy.IntegrationTestSupport;
import ds.project.toy.api.controller.user.dto.response.ChangeNicknameResponse;
import ds.project.toy.api.controller.user.dto.response.ChangeProfileImageResponse;
import ds.project.toy.api.controller.user.dto.response.GetUserProfileResponse;
import ds.project.toy.api.service.user.dto.ChangeNicknameServiceDto;
import ds.project.toy.api.service.user.dto.ChangeProfileImageDto;
import ds.project.toy.api.service.user.dto.GetUserProfileDto;
import ds.project.toy.domain.user.entity.SocialProvider;
import ds.project.toy.domain.user.entity.UserInfo;
import ds.project.toy.global.common.exception.CustomException;
import ds.project.toy.global.common.exception.ResponseCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

class UserServiceTest extends IntegrationTestSupport {

    @DisplayName(value = "변경할 닉네임을 받아 기존 닉네임을 변경한다.")
    @Test
    void changeNickname() {
        //given
        String prevNickname = "prev";
        String nextNickname = "nickname";
        SocialProvider socialProvider = socialProviderRepository.save(createSocialProvider());
        UserInfo userInfo = createUserInfo(prevNickname);
        socialLoginRepository.save(createSocialLogin(socialProvider, userInfo));

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
    void changeNicknameWithNicknameAlreadyInUse() {
        //given
        String nickname = "nickname";
        SocialProvider socialProvider = socialProviderRepository.save(createSocialProvider());
        UserInfo userInfo = createUserInfo(nickname);
        socialLoginRepository.save(createSocialLogin(socialProvider, userInfo));
        ChangeNicknameServiceDto dto = ChangeNicknameServiceDto.of(nickname, userInfo.getUserId());

        //when then
        assertThatThrownBy(() -> userService.changeNickname(dto))
            .isInstanceOf(CustomException.class)
            .extracting("responseCode")
            .isEqualTo(ResponseCode.NICKNAME_ALREADY_IN_USE);
    }

    @DisplayName(value = "닉네임을 변경할 때 중복된 닉네임일 경우 예외가 발생한다.")
    @Test
    void changeNicknameWithDuplicateNickname() {
        //given
        String prevNickname = "prev";
        String duplicateNickname = "nickname";
        SocialProvider socialProvider = socialProviderRepository.save(createSocialProvider());
        UserInfo userInfo = createUserInfo(prevNickname);
        socialLoginRepository.save(createSocialLogin(socialProvider, userInfo));
        UserInfo userInfo2 = createUserInfo(duplicateNickname);
        socialLoginRepository.save(createSocialLogin(socialProvider, userInfo2));

        ChangeNicknameServiceDto dto = ChangeNicknameServiceDto.of(duplicateNickname,
            userInfo.getUserId());

        //when then
        assertThatThrownBy(() -> userService.changeNickname(dto))
            .isInstanceOf(CustomException.class)
            .extracting("responseCode")
            .isEqualTo(ResponseCode.DUPLICATE_NICKNAME);
    }

    @DisplayName(value = "변경할 이미지를 받아 프로필 이미지를 수정한다.")
    @Test
    void changeProfileImage() {
        //given
        MockMultipartFile image = createImageMockMultipartFile("image");
        UserInfo userInfo = userInfoRepository.save(createUserInfo("nickname"));
        ChangeProfileImageDto dto = ChangeProfileImageDto.of(userInfo.getUserId(), image);
        String imageUrl = "image.url";
        given(minioUtil.uploadFile(any())).willReturn(imageUrl);

        //when
        ChangeProfileImageResponse response = userService.changeProfileImage(dto);

        //then
        assertThat(response).isNotNull();
        assertThat(response.getProfileImage()).isEqualTo(imageUrl);
    }

    @DisplayName(value = "프로필 이미지를 수정할 때 기존이미지가 있으면 기존이미지를 삭제한다.")
    @Test
    void changeProfileImageWithExistsProfileImage() {
        //given
        MockMultipartFile image = createImageMockMultipartFile("image");
        UserInfo userInfo = userInfoRepository.save(
            createUserInfo());
        ChangeProfileImageDto dto = ChangeProfileImageDto.of(userInfo.getUserId(), image);
        String imageUrl = "changeImage.jpg";
        given(minioUtil.uploadFile(any())).willReturn(imageUrl);

        //when
        ChangeProfileImageResponse response = userService.changeProfileImage(dto);

        //then
        assertThat(response).isNotNull();
        assertThat(response.getProfileImage()).isEqualTo(imageUrl);
        verify(minioUtil, times(1)).deleteFile(any());
    }

    @DisplayName(value = "유저 id로 유저 정보를 조회한다.")
    @Test
    void getUserProfile() {
        //given
        String nickname = "nickname";
        String email = "email@email.com";
        String profileImage = "profileImage.jpg";
        UserInfo userInfo = userInfoRepository.save(createUserInfo(nickname, email, profileImage));
        GetUserProfileDto dto = GetUserProfileDto.of(userInfo.getUserId());

        //when
        GetUserProfileResponse response = userService.getUserProfile(dto);

        //then
        assertThat(response)
            .extracting("nickname", "email", "profileImage")
            .containsExactly(nickname, email, profileImage);
    }

}