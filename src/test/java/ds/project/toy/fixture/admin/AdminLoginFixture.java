package ds.project.toy.fixture.admin;

import ds.project.toy.domain.user.entity.AdminLogin;
import ds.project.toy.domain.user.entity.UserInfo;

public class AdminLoginFixture {
    public static AdminLogin createAdminLogin(UserInfo userInfo, String id, String password,
        String salt) {
        return AdminLogin.of(userInfo, id, password, salt);
    }
}
