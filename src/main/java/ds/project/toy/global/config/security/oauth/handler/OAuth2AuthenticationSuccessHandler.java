package ds.project.toy.global.config.security.oauth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import ds.project.toy.global.common.exception.ResponseCode;
import ds.project.toy.global.common.vo.AuthToken;
import ds.project.toy.global.config.security.jwt.JwtTokenProvider;
import ds.project.toy.global.config.security.oauth.user.core.OAuth2UserImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException {
        Long userId = extractUserId(authentication);
        AuthToken authToken = generateAuthToken(userId);

        respondWithToken(response, authToken);
    }

    private Long extractUserId(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof OAuth2UserImpl) {
            return ((OAuth2UserImpl) principal).getUserId();
        }
        return null;
    }

    private AuthToken generateAuthToken(Long userId) {
        return jwtTokenProvider.createToken(String.valueOf(userId));
    }

    private void respondWithToken(HttpServletResponse response, AuthToken authToken)
        throws IOException {
        objectMapper.writeValue(response.getWriter(), authToken);
    }

}
