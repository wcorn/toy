package ds.project.toy.api.controller.user;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ds.project.toy.ControllerTestSupport;
import ds.project.toy.api.controller.user.dto.request.ChangeNicknameRequest;
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
    public void changeNickname() throws Exception {
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
    public void changeProfileImage() throws Exception {
        //given
        MockMultipartFile image = new MockMultipartFile("image", "test.jpeg",
            MediaType.IMAGE_JPEG_VALUE, "test".getBytes());
        given(fileUtil.isImageFile(any())).willReturn(true);
        //when
        mockMvc.perform(
                multipart(HttpMethod.PATCH, "/user/profile_image")
                    .file(image)
                    .with(csrf())
            )
            .andExpect(status().isOk());
        //then

    }
}