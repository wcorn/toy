package ds.project.toy.api.controller.user;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ds.project.toy.ControllerTestSupport;
import ds.project.toy.api.controller.user.dto.request.ChangeNicknameRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
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
}