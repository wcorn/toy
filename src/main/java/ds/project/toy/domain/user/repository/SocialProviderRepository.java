package ds.project.toy.domain.user.repository;

import ds.project.toy.domain.user.entity.SocialProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocialProviderRepository extends JpaRepository<SocialProvider, String> {

}
