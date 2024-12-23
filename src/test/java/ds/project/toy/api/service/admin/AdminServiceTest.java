package ds.project.toy.api.service.admin;

import static ds.project.toy.fixture.admin.AdminLoginFixture.createAdminLogin;
import static ds.project.toy.fixture.product.CategoryFixture.createCategory;
import static ds.project.toy.fixture.user.UserInfoFixture.createUserInfo;
import static org.assertj.core.api.Assertions.*;

import ds.project.toy.IntegrationTestSupport;
import ds.project.toy.api.controller.admin.dto.response.GetCategoryResponse;
import ds.project.toy.api.controller.admin.dto.response.PostCategoryResponse;
import ds.project.toy.api.service.admin.dto.AdminLoginServiceDto;
import ds.project.toy.api.service.admin.dto.DeleteCategoryServiceDto;
import ds.project.toy.api.service.admin.dto.PostCategoryServiceDto;
import ds.project.toy.domain.product.entity.Category;
import ds.project.toy.domain.product.vo.CategoryState;
import ds.project.toy.domain.user.entity.AdminLogin;
import ds.project.toy.domain.user.entity.UserInfo;
import ds.project.toy.global.common.exception.CustomException;
import ds.project.toy.global.common.exception.ResponseCode;
import ds.project.toy.global.common.vo.AuthToken;
import java.util.List;
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
        String encodedPassword = passwordEncoder.encode(password + salt);
        UserInfo userInfo = createUserInfo();
        AdminLogin adminLogin = adminLoginRepository.save(
            createAdminLogin(userInfo, id, encodedPassword, salt));
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
        String encodedPassword = passwordEncoder.encode(password + salt);
        UserInfo userInfo = createUserInfo();
        adminLoginRepository.save(createAdminLogin(userInfo, id, encodedPassword, salt));
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
        String encodedPassword = passwordEncoder.encode(password + salt);
        UserInfo userInfo = createUserInfo();
        adminLoginRepository.save(createAdminLogin(userInfo, id, encodedPassword, salt));
        AdminLoginServiceDto dto = AdminLoginServiceDto.of(id, wrongPassword);
        //when
        assertThatThrownBy(() -> adminService.adminLogin(dto))
            .isInstanceOf(CustomException.class)
            .extracting("responseCode")
            .isEqualTo(ResponseCode.FAILED_ADMIN_LOGIN);
    }

    @DisplayName(value = "카테고리를 조회한다.")
    @Test
    void getCategory() {
        //given
        String ActiveCategoryContent = "전자기기";
        String InActiveCategoryContent = "전자기기";
        Category category1 = createCategory(ActiveCategoryContent, CategoryState.ACTIVE);
        Category category2 = createCategory(InActiveCategoryContent, CategoryState.INACTIVE);
        List<Category> categories = categoryRepository.saveAll(List.of(category1, category2));
        //when
        List<GetCategoryResponse> response = adminService.getCategory();
        //then
        assertThat(response)
            .extracting("categoryId", "categoryName", "categoryState")
            .containsExactly(
                tuple(categories.get(0).getCategoryId(), ActiveCategoryContent,
                    CategoryState.ACTIVE.getName()),
                tuple(categories.get(1).getCategoryId(), InActiveCategoryContent,
                    CategoryState.INACTIVE.getName())
            );
    }

    @DisplayName(value = "카테고리를 등록한다.")
    @Test
    void postCategory() {
        //given
        String categoryContent = "전자기기";
        PostCategoryServiceDto dto = PostCategoryServiceDto.of(categoryContent);

        //when
        PostCategoryResponse response = adminService.postCategory(dto);
        //then
        List<Category> categories = categoryRepository.findAll();
        assertThat(response.getCategoryId()).isEqualTo(categories.get(0).getCategoryId());
        assertThat(categories).extracting("content", "categoryState")
            .containsExactly(tuple(categoryContent, CategoryState.ACTIVE));
    }

    @DisplayName(value = "카테고리를 삭제한다.")
    @Test
    void deleteCategory() {
        //given
        String categoryContent = "전자기기";
        Category category = categoryRepository.save(createCategory(categoryContent));
        DeleteCategoryServiceDto dto = DeleteCategoryServiceDto.of(category.getCategoryId());
        //when
        adminService.deleteCategory(dto);
        //then
        List<Category> categories = categoryRepository.findAll();
        assertThat(categories).extracting("content", "categoryState")
            .containsExactly(tuple(categoryContent, CategoryState.INACTIVE));
    }

}