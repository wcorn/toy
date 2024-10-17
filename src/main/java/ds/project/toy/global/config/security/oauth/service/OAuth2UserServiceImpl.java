package ds.project.toy.global.config.security.oauth.service;

import ds.project.toy.domain.user.entity.SocialLogin;
import ds.project.toy.domain.user.entity.SocialProvider;
import ds.project.toy.domain.user.entity.UserInfo;
import ds.project.toy.domain.user.repository.SocialLoginRepository;
import ds.project.toy.domain.user.repository.SocialProviderRepository;
import ds.project.toy.domain.user.vo.SocialProviderState;
import ds.project.toy.global.common.vo.OAuth2Provider;
import ds.project.toy.global.config.security.oauth.user.core.OAuth2UserImpl;
import ds.project.toy.global.config.security.oauth.user.core.OAuth2UserInfo;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Transactional
@Service
public class OAuth2UserServiceImpl extends DefaultOAuth2UserService {

    private final SocialLoginRepository socialLoginRepository;
    private final SocialProviderRepository socialProviderRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        return processOAuth2User(oAuth2UserRequest, oAuth2User);

    }

    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(
            registrationId, oAuth2User.getAttributes()
        );
        SocialLogin socialLogin = findOrCreateSocialLogin(oAuth2UserInfo);

        return new OAuth2UserImpl(oAuth2UserInfo, socialLogin.getUserId());
    }

    private SocialLogin findOrCreateSocialLogin(OAuth2UserInfo oAuth2UserInfo) {
        Optional<SocialLogin> socialLogin = socialLoginRepository.findByProviderProviderAndSocialId(
            oAuth2UserInfo.getProvider(), oAuth2UserInfo.getId());

        return socialLogin.orElseGet(() -> createNewSocialLogin(oAuth2UserInfo));
    }

    private SocialLogin createNewSocialLogin(OAuth2UserInfo oAuth2UserInfo) {
        UserInfo userInfo = UserInfo.creatUser(oAuth2UserInfo.getEmail(),
            oAuth2UserInfo.getNickname());
        SocialProvider socialProvider = findActiveSocialProvider(oAuth2UserInfo.getProvider());
        SocialLogin newSocialLogin = SocialLogin.create(userInfo, socialProvider,
            oAuth2UserInfo.getId());
        return socialLoginRepository.save(newSocialLogin);
    }

    private SocialProvider findActiveSocialProvider(OAuth2Provider provider) {
        return socialProviderRepository.findByProviderAndState(provider, SocialProviderState.ACTIVE)
            .orElseThrow(() -> new IllegalStateException("Active social provider not found"));
    }
}
