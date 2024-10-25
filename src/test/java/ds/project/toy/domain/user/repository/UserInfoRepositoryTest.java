package ds.project.toy.domain.user.repository;

import static org.junit.jupiter.api.Assertions.*;

import ds.project.toy.IntegrationTestSupport;
import ds.project.toy.domain.user.entity.UserInfo;
import ds.project.toy.domain.user.vo.UserInfoRole;
import ds.project.toy.domain.user.vo.UserInfoState;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserInfoRepositoryTest extends IntegrationTestSupport {

    @DisplayName(value = "유저 ID와 state를 가진 유저정보를 조회한다.")
    @Test
    public void findByUserIdAndState() {
        //given
        UserInfo userInfo1 = createUserInfo("nickname1", "email1@gmail.com",
            UserInfoRole.ROLE_USER, UserInfoState.ACTIVE);
        UserInfo userInfo2 = createUserInfo("nickname2", "email2@gmail.com",
            UserInfoRole.ROLE_USER, UserInfoState.BAN);
        UserInfo userInfo3 = createUserInfo("nickname3", "email3@gmail.com",
            UserInfoRole.ROLE_USER, UserInfoState.WITHDRAWN);
        userInfoRepository.saveAll(List.of(userInfo1, userInfo2, userInfo3));

        //when
        Optional<UserInfo> savedUserInfo = userInfoRepository.findByUserIdAndState(
            userInfo1.getUserId(), userInfo1.getState());

        //then
        assertTrue(savedUserInfo.isPresent());
        assertEquals(userInfo1.getUserId(), savedUserInfo.get().getUserId());
        assertEquals(userInfo1.getState(), savedUserInfo.get().getState());

    }

    @DisplayName(value = "닉네임과 상태로 활성화된 유저가 존재하는지 확인한다.")
    @Test
    public void existsByNicknameAndState() {
        //given
        String nickname = "nickname1";
        UserInfo userInfo1 = createUserInfo(nickname, "email1@gmail.com",
            UserInfoRole.ROLE_USER, UserInfoState.ACTIVE);
        UserInfo userInfo2 = createUserInfo("nickname2", "email2@gmail.com",
            UserInfoRole.ROLE_USER, UserInfoState.BAN);
        UserInfo userInfo3 = createUserInfo("nickname3", "email3@gmail.com",
            UserInfoRole.ROLE_USER, UserInfoState.WITHDRAWN);
        userInfoRepository.saveAll(List.of(userInfo1, userInfo2, userInfo3));
        //when
        boolean exists = userInfoRepository.existsByNicknameAndState(nickname, UserInfoState.ACTIVE);
        //then
        assertTrue(exists);
    }

    private UserInfo createUserInfo(String nickname, String email,
        UserInfoRole role, UserInfoState state) {
        return UserInfo.of(nickname, email, role, state);
    }
}