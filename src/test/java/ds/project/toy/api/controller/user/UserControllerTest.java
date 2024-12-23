package ds.project.toy.api.controller.user;

import static ds.project.toy.fixture.file.MultipartFileFixture.createImageMockMultipartFile;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ds.project.toy.ControllerTestSupport;
import ds.project.toy.api.controller.user.dto.request.ChangeNicknameRequest;
import ds.project.toy.api.controller.user.dto.response.GetUserProfileResponse;
import ds.project.toy.global.common.exception.ResponseCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;

class UserControllerTest extends ControllerTestSupport {

    @DisplayName(value = "닉네임을 변경한다.")
    @Test
    @WithMockUser(roles = "USER", username = "1")
    void changeNickname() throws Exception {
        //given
        String nickname = "nickname";
        ChangeNicknameRequest request = ChangeNicknameRequest.of(nickname);
        //when
        mockMvc.perform(patch("/user/nickname")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON).with(csrf()))
            .andExpect(status().isOk());
        //then

    }

    @DisplayName(value = "프로필 이미지를 변경한다.")
    @Test
    @WithMockUser(roles = "USER", username = "1")
    void changeProfileImage() throws Exception {
        //given
        MockMultipartFile image = createImageMockMultipartFile("image");
        given(fileUtil.isImageFile(any())).willReturn(true);
        //when
        mockMvc.perform(
                multipart(HttpMethod.PATCH, "/user/profile-image")
                    .file(image)
                    .with(csrf())
            )
            .andExpect(status().isOk());
        //then

    }

    @DisplayName(value = "프로필 이미지를 변경할때 유효한 이미지가 아닌 경우 예외가 발생한다.")
    @Test
    @WithMockUser(roles = "USER", username = "1")
    void changeProfileImageWithInvalidImage() throws Exception {
        //given
        MockMultipartFile image = createImageMockMultipartFile("image");
        given(fileUtil.isImageFile(any())).willReturn(false);
        //when
        mockMvc.perform(
                multipart(HttpMethod.PATCH, "/user/profile-image")
                    .file(image)
                    .with(csrf())
            )
            .andExpect(status().isBadRequest())
            .andExpectAll(
                jsonPath("$.code").value(ResponseCode.NOT_IMAGE.getCode()),
                jsonPath("$.message").value(ResponseCode.NOT_IMAGE.getMessage())
            );
        //then

    }

    @DisplayName(value = "프로필을 조회한다.")
    @Test
    @WithMockUser(roles = "USER", username = "1")
    void getUserProfile() throws Exception {
        //given
        String nickname = "nickname";
        String profileImage = "test.jpg";
        String email = "email@email.com";
        GetUserProfileResponse response = GetUserProfileResponse.of(nickname, email, profileImage);
        given(userService.getUserProfile(any())).willReturn(response);
        //when
        mockMvc.perform(
                get("/user/profile")
                    .with(csrf())
            )
            .andExpect(status().isOk())
            .andExpectAll(
                jsonPath("$.nickname").value(nickname),
                jsonPath("$.email").value(email),
                jsonPath("$.profileImage").value(profileImage)
            );
        //then

    }
}