package ds.project.toy.global.config.security.oauth.user.kakao;

import ds.project.toy.global.common.vo.OAuth2Provider;
import ds.project.toy.global.config.security.oauth.user.core.OAuth2UserInfo;
import java.util.Map;

public class KakaoOAuth2UserInfo implements OAuth2UserInfo {

    private final String id;
    private final Map<String, Object> attributes;
    private final String nickName;
    private final String email;

    public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");
        this.attributes = attributes;
        this.id = ((Long) attributes.get("id")).toString();
        this.nickName = (String) kakaoProfile.get("nickname");
        this.email = (String) kakaoAccount.get("email");
    }

    @Override
    public OAuth2Provider getProvider() {
        return OAuth2Provider.KAKAO;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public String getNickname() {
        return this.nickName;
    }
}
