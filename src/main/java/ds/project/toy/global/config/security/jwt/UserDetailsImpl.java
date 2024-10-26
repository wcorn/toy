package ds.project.toy.global.config.security.jwt;

import ds.project.toy.domain.user.entity.UserInfo;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsImpl extends UserInfo implements UserDetails {

    public UserDetailsImpl(UserInfo user) {
        super(user);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(super.getRole().toString()));
    }

    @Override
    public String getPassword() {
        return super.getRole().getName();
    }

    @Override
    public String getUsername() {
        return String.valueOf(super.getUserId());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
