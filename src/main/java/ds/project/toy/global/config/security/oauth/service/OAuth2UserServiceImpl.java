package ds.project.toy.global.config.security.oauth.service;

import ds.project.toy.global.config.security.oauth.user.core.OAuth2UserImpl;
import ds.project.toy.global.config.security.oauth.user.core.OAuth2UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class OAuth2UserServiceImpl extends DefaultOAuth2UserService {

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

        return new OAuth2UserImpl(oAuth2UserInfo);
    }
}
