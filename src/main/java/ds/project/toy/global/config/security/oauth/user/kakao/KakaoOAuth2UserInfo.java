package ds.project.toy.global.config.security.oauth.user.kakao;

import ds.project.toy.global.common.vo.OAuth2Provider;
import ds.project.toy.global.config.security.oauth.user.core.OAuth2UserInfo;
import java.util.Map;

public class KakaoOAuth2UserInfo implements OAuth2UserInfo {

    private final String id;
    private final Map<String, Object> attributes;

    public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
        this.id = ((Long) attributes.get("id")).toString();
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
        return id;
    }
}
