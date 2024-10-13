package ds.project.toy.domain.user.repository;

import ds.project.toy.domain.user.entity.SocialLogin;
import ds.project.toy.global.common.vo.OAuth2Provider;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocialLoginRepository extends JpaRepository<SocialLogin, Long> {

    Optional<SocialLogin> findByProviderProviderAndSocialId(OAuth2Provider provider, String id);
}
