package ds.project.toy.domain.user.repository;

import ds.project.toy.domain.user.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

}
