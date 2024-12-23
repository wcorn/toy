package ds.project.toy.domain.user.repository;

import static ds.project.toy.fixture.user.UserInfoFixture.createUserInfo;
import static org.junit.jupiter.api.Assertions.*;

import ds.project.toy.IntegrationTestSupport;
import ds.project.toy.domain.user.entity.UserInfo;
import ds.project.toy.domain.user.vo.UserInfoState;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserInfoRepositoryTest extends IntegrationTestSupport {

    @DisplayName(value = "유저 ID와 state를 가진 유저정보를 조회한다.")
    @Test
    void findByUserIdAndState() {
        //given
        UserInfo activeUserInfo = createUserInfo("nickname1", UserInfoState.ACTIVE);
        UserInfo banUserInfo = createUserInfo("nickname2", UserInfoState.BAN);
        UserInfo inActiveUserInfo = createUserInfo("nickname3", UserInfoState.WITHDRAWN);
        userInfoRepository.saveAll(List.of(activeUserInfo, banUserInfo, inActiveUserInfo));

        //when
        Optional<UserInfo> savedUserInfo = userInfoRepository.findByUserIdAndState(
            activeUserInfo.getUserId(), activeUserInfo.getState());

        //then
        assertTrue(savedUserInfo.isPresent());
        assertEquals(activeUserInfo.getUserId(), savedUserInfo.get().getUserId());
        assertEquals(activeUserInfo.getState(), savedUserInfo.get().getState());

    }

    @DisplayName(value = "닉네임과 상태로 활성화된 유저가 존재하는지 확인한다.")
    @Test
    void existsByNicknameAndState() {
        //given
        String nickname = "nickname1";
        UserInfo userInfo1 = createUserInfo(nickname, UserInfoState.ACTIVE);
        UserInfo userInfo2 = createUserInfo("nickname2", UserInfoState.BAN);
        UserInfo userInfo3 = createUserInfo("nickname3", UserInfoState.WITHDRAWN);
        userInfoRepository.saveAll(List.of(userInfo1, userInfo2, userInfo3));
        //when
        boolean exists = userInfoRepository.existsByNicknameAndState(nickname,
            UserInfoState.ACTIVE);
        //then
        assertTrue(exists);
    }
}