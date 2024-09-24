package ds.project.toy.domain.user.repository;

import ds.project.toy.domain.user.entity.SocialLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocialLoginRepository extends JpaRepository<SocialLogin, Long> {

}
