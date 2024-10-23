package ds.project.toy.domain.user.repository;

import ds.project.toy.domain.user.entity.UserInfo;
import ds.project.toy.domain.user.vo.UserInfoState;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

    Optional<UserInfo> findByUserIdAndState(Long userId, UserInfoState state);

    boolean existsByNicknameAndState(String nickname, UserInfoState userInfoState);
}
