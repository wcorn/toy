package ds.project.toy.global.config.security.oauth.user.core;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

@ToString
@Getter
public class OAuth2UserImpl implements OAuth2User {

    private final OAuth2UserInfo userInfo;

    public OAuth2UserImpl(OAuth2UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return userInfo.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getName() {
        return userInfo.getId();
    }

}