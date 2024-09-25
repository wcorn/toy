package ds.project.toy.domain.user.repository;

import ds.project.toy.domain.user.entity.AdminLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminLoginRepository extends JpaRepository<AdminLogin, Long> {

}
