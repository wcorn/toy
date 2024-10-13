package ds.project.toy.global.config.security.oauth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import ds.project.toy.domain.user.entity.SocialLogin;
import ds.project.toy.domain.user.entity.SocialProvider;
import ds.project.toy.domain.user.entity.UserInfo;
import ds.project.toy.domain.user.repository.SocialLoginRepository;
import ds.project.toy.domain.user.repository.SocialProviderRepository;
import ds.project.toy.domain.user.vo.SocialProviderState;
import ds.project.toy.global.common.exception.ResponseCode;
import ds.project.toy.global.common.vo.AuthToken;
import ds.project.toy.global.common.vo.OAuth2Provider;
import ds.project.toy.global.config.security.jwt.JwtTokenProvider;
import ds.project.toy.global.config.security.oauth.user.core.OAuth2UserImpl;
import ds.project.toy.global.config.security.oauth.user.core.OAuth2UserInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
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
    private final SocialLoginRepository socialLoginRepository;
    private final SocialProviderRepository socialProviderRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException {
        OAuth2UserInfo oAuth2UserInfo = extractOAuth2UserInfo(authentication);

        if (oAuth2UserInfo == null) {
            respondWithUnauthorized(response);
            return;
        }

        Optional<AuthToken> authToken = generateAuthToken(oAuth2UserInfo);

        if (authToken.isPresent()) {
            respondWithToken(response, authToken.get());
        } else {
            respondWithUnauthorized(response);
        }
    }

    private OAuth2UserInfo extractOAuth2UserInfo(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof OAuth2UserImpl) {
            return ((OAuth2UserImpl) principal).getUserInfo();
        }
        return null;
    }

    private Optional<AuthToken> generateAuthToken(OAuth2UserInfo oAuth2UserInfo) {
        return findOrCreateSocialLogin(oAuth2UserInfo)
            .map(this::createAuthToken);
    }

    private Optional<SocialLogin> findOrCreateSocialLogin(OAuth2UserInfo oAuth2UserInfo) {
        Optional<SocialLogin> socialLogin = socialLoginRepository.findByProviderProviderAndSocialId(
            oAuth2UserInfo.getProvider(), oAuth2UserInfo.getId());

        if (socialLogin.isEmpty()) {
            return createNewSocialLogin(oAuth2UserInfo);
        }

        return socialLogin;
    }

    private Optional<SocialLogin> createNewSocialLogin(OAuth2UserInfo oAuth2UserInfo) {
        UserInfo userInfo = UserInfo.createGuest();
        SocialProvider socialProvider = findActiveSocialProvider(oAuth2UserInfo.getProvider());
        SocialLogin newSocialLogin = SocialLogin.create(userInfo, socialProvider,
            oAuth2UserInfo.getId());
        return Optional.of(socialLoginRepository.save(newSocialLogin));
    }

    private SocialProvider findActiveSocialProvider(OAuth2Provider provider) {
        return socialProviderRepository.findByProviderAndState(provider, SocialProviderState.ACTIVE)
            .orElseThrow(() -> new IllegalStateException("Active social provider not found"));
    }

    private AuthToken createAuthToken(SocialLogin socialLogin) {
        AuthToken authToken = jwtTokenProvider.createToken(String.valueOf(socialLogin.getUserId()));
        authToken.setRole(socialLogin.getUserInfo().getRole());
        return authToken;
    }

    private void respondWithToken(HttpServletResponse response, AuthToken authToken)
        throws IOException {
        objectMapper.writeValue(response.getWriter(), authToken);
    }

    private void respondWithUnauthorized(HttpServletResponse response) throws IOException {
        objectMapper.writeValue(response.getWriter(), ResponseCode.UNAUTHORIZED);
    }
}
