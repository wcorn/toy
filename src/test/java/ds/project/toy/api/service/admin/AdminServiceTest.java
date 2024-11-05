package ds.project.toy.api.service.admin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import ds.project.toy.IntegrationTestSupport;
import ds.project.toy.api.service.admin.dto.AdminLoginServiceDto;
import ds.project.toy.domain.user.entity.AdminLogin;
import ds.project.toy.domain.user.entity.UserInfo;
import ds.project.toy.domain.user.vo.UserInfoRole;
import ds.project.toy.domain.user.vo.UserInfoState;
import ds.project.toy.global.common.exception.CustomException;
import ds.project.toy.global.common.exception.ResponseCode;
import ds.project.toy.global.common.vo.AuthToken;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AdminServiceTest extends IntegrationTestSupport {

    @DisplayName(value = "id와 password로 관리자 로그인 한다.")
    @Test
    void adminLogin() {
        //given
        String id = "id";
        String password = "password";
        String salt = "s1a2d3f4g4";
        String encodedPassword = passwordEncoder.encode(password+salt);
        UserInfo userInfo = createUserInfo("nickname", "email@gmai.com", UserInfoRole.ROLE_ADMIN,
            UserInfoState.ACTIVE);
        AdminLogin adminLogin = adminLoginRepository.save(createAdminLogin(userInfo, id, encodedPassword,salt));
        AdminLoginServiceDto dto = AdminLoginServiceDto.of(id, password);
        //when
        AuthToken authToken = adminService.adminLogin(dto);
        //then
        assertThat(adminLogin.getAdminId()).hasToString(
            jwtTokenProvider.getAuthentication(authToken.getAccessToken()).getName());
    }

    @DisplayName(value = "id와 password로 관리자 로그인 할때 id가 틀릴 경우 예외가 발생한다.")
    @Test
    void adminLoginWithWrongID() {
        //given
        String id = "id";
        String wrongId = "wrongId";
        String password = "password";
        String salt = "s1a2d3f4g4";
        String encodedPassword = passwordEncoder.encode(password+salt);
        UserInfo userInfo = createUserInfo("nickname", "email@gmai.com", UserInfoRole.ROLE_ADMIN,
            UserInfoState.ACTIVE);
        adminLoginRepository.save(createAdminLogin(userInfo, id, encodedPassword,salt));
        AdminLoginServiceDto dto = AdminLoginServiceDto.of(wrongId, password);
        //when
        assertThatThrownBy(() -> adminService.adminLogin(dto))
            .isInstanceOf(CustomException.class)
            .extracting("responseCode")
            .isEqualTo(ResponseCode.FAILED_ADMIN_LOGIN);
    }

    @DisplayName(value = "id와 password로 관리자 로그인 할때 password가 틀릴 경우 예외가 발생한다.")
    @Test
    void adminLoginWithWrongPassword() {
        //given
        String id = "id";
        String wrongPassword = "wrongPassword";
        String password = "password";
        String salt = "s1a2d3f4g4";
        String encodedPassword = passwordEncoder.encode(password+salt);
        UserInfo userInfo = createUserInfo("nickname", "email@gmai.com", UserInfoRole.ROLE_ADMIN,
            UserInfoState.ACTIVE);
        adminLoginRepository.save(createAdminLogin(userInfo, id, encodedPassword,salt));
        AdminLoginServiceDto dto = AdminLoginServiceDto.of(id, wrongPassword);
        //when
        assertThatThrownBy(() -> adminService.adminLogin(dto))
            .isInstanceOf(CustomException.class)
            .extracting("responseCode")
            .isEqualTo(ResponseCode.FAILED_ADMIN_LOGIN);
    }

    private UserInfo createUserInfo(String nickname, String email,
        UserInfoRole role, UserInfoState state) {
        return UserInfo.of(nickname, email, role, state);
    }

    private AdminLogin createAdminLogin(UserInfo userInfo, String id, String password, String salt) {
        return AdminLogin.of(userInfo, id, password, salt);
    }
}