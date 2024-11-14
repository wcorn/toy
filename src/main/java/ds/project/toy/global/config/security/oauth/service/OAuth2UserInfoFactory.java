package ds.project.toy.global.config.security.oauth.service;

import ds.project.toy.global.common.exception.CustomException;
import ds.project.toy.global.common.exception.ResponseCode;
import ds.project.toy.global.common.vo.OAuth2Provider;
import ds.project.toy.global.config.security.oauth.user.kakao.KakaoOAuth2UserInfo;
import ds.project.toy.global.config.security.oauth.user.core.OAuth2UserInfo;
import java.util.Map;

public class OAuth2UserInfoFactory {

    private OAuth2UserInfoFactory() {
    }

    public static OAuth2UserInfo getOAuth2UserInfo(
        String registrationId, Map<String, Object> attributes) {
        if (OAuth2Provider.KAKAO.getRegistrationId().equals(registrationId)) {
            return new KakaoOAuth2UserInfo(attributes);
        } else {
            throw new CustomException(ResponseCode.BAD_REQUEST);
        }
    }
}
