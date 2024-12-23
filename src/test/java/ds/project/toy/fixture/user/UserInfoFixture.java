package ds.project.toy.fixture.user;

import ds.project.toy.domain.user.entity.UserInfo;
import ds.project.toy.domain.user.vo.UserInfoRole;
import ds.project.toy.domain.user.vo.UserInfoState;

public class UserInfoFixture {
    public static UserInfo createUserInfo() {
        return UserInfo.of("nickname", "email@gmail.com", "imgae.jpeg",
            UserInfoRole.ROLE_USER, UserInfoState.ACTIVE);
    }
    public static UserInfo createUserInfo(String nickname) {
        return UserInfo.of(nickname, "email", UserInfoRole.ROLE_USER, UserInfoState.ACTIVE);
    }
    public static UserInfo createUserInfo(String nickname, String email, UserInfoState state) {
        return UserInfo.of(nickname, email, "image.jpeg", UserInfoRole.ROLE_USER, state);
    }
    public static UserInfo createUserInfo(String nickname, UserInfoState state) {
        return UserInfo.of(nickname, "email@gmail.com", "image.jpeg", UserInfoRole.ROLE_USER, state);
    }
    public static UserInfo createUserInfo(String nickname, String email, String image) {
        return UserInfo.of(nickname, email, image, UserInfoRole.ROLE_USER, UserInfoState.ACTIVE);
    }
}
