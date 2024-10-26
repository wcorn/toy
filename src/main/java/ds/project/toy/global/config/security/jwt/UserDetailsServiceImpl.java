package ds.project.toy.global.config.security.jwt;

import ds.project.toy.domain.user.entity.UserInfo;
import ds.project.toy.domain.user.repository.UserInfoRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserInfoRepository userInfoRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<UserInfo> optionalUser = userInfoRepository.findById(Long.valueOf(username));
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        UserDetails user = new UserDetailsImpl(optionalUser.get());
        return User.withUsername(user.getUsername())
            .password(user.getPassword())
            .authorities(user.getAuthorities())
            .build();
    }
}