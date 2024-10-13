package ds.project.toy.domain.user.repository;

import ds.project.toy.domain.user.entity.SocialProvider;
import ds.project.toy.domain.user.vo.SocialProviderState;
import ds.project.toy.global.common.vo.OAuth2Provider;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocialProviderRepository extends JpaRepository<SocialProvider, String> {

    Optional<SocialProvider> findByProviderAndState(OAuth2Provider provider,
        SocialProviderState socialProviderState);
}
