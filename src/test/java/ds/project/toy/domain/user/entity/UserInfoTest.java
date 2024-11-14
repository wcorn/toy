package ds.project.toy.domain.user.entity;

import static org.assertj.core.api.Assertions.assertThat;

import ds.project.toy.IntegrationTestSupport;
import ds.project.toy.domain.user.vo.UserInfoRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserInfoTest extends IntegrationTestSupport {

    @DisplayName(value = "user role을 가진 userInfo를 생성한다.")
    @Test
    void creatUser() {
        //given when
        UserInfo userInfo = UserInfo.creatUser("email", "nickname");

        //then
        assertThat(userInfo.getRole()).isEqualTo(UserInfoRole.ROLE_USER);
    }
}