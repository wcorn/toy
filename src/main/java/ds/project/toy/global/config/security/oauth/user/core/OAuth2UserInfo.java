package ds.project.toy.global.config.security.oauth.user.core;

import ds.project.toy.global.common.vo.OAuth2Provider;
import java.util.Map;

public interface OAuth2UserInfo {

    OAuth2Provider getProvider();

    Map<String, Object> getAttributes();

    String getId();

    String getEmail();

    String getNickname();
}
