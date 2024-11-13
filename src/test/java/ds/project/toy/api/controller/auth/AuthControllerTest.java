package ds.project.toy.api.controller.auth;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.BDDMockito.given;

import ds.project.toy.ControllerTestSupport;
import ds.project.toy.api.controller.auth.dto.request.ReissuedTokenRequest;
import ds.project.toy.global.common.vo.AuthToken;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class AuthControllerTest extends ControllerTestSupport {

    @DisplayName(value = "토큰을 재발급한다.")
    @Test
    void reissuedToken() throws Exception {
        //given
        ReissuedTokenRequest request = ReissuedTokenRequest.of("accessToken", "refreshToken");
        AuthToken authToken = AuthToken.of("accessToken2", "refreshToken2");

        given(authService.reissuedToken(any())).willReturn(
            AuthToken.of(authToken.getAccessToken(), authToken.getRefreshToken()));
        //when then
        mockMvc.perform(
                post("/auth/token/reissue")
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