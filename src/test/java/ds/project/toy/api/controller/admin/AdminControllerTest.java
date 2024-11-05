package ds.project.toy.api.controller.admin;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ds.project.toy.ControllerTestSupport;
import ds.project.toy.api.controller.admin.dto.request.AdminLoginRequest;
import ds.project.toy.global.common.vo.AuthToken;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;


class AdminControllerTest extends ControllerTestSupport {

    @DisplayName(value = "관리자가 로그인 한다.")
    @Test
    void adminLogin() throws Exception {
        //given
        String id = "id";
        String password = "password";
        AdminLoginRequest request = AdminLoginRequest.of(id, password);
        AuthToken authToken = AuthToken.of("accessToken2", "refreshToken2");
        given(adminService.adminLogin(any()))
            .willReturn(authToken);
        //when then
        mockMvc.perform(
                post("/admin/login")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON).with(csrf())
            )
            .andExpect(status().isOk())
            .andExpectAll(
                jsonPath("$.accessToken").value(authToken.getAccessToken()),
                jsonPath("$.refreshToken").value(authToken.getRefreshToken())
            );
    }
}