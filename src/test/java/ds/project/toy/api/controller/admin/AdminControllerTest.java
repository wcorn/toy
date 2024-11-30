package ds.project.toy.api.controller.admin;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ds.project.toy.ControllerTestSupport;
import ds.project.toy.api.controller.admin.dto.request.AdminLoginRequest;
import ds.project.toy.api.controller.admin.dto.request.PostCategoryRequest;
import ds.project.toy.api.controller.admin.dto.response.GetCategoryResponse;
import ds.project.toy.api.controller.admin.dto.response.PostCategoryResponse;
import ds.project.toy.domain.product.vo.CategoryState;
import ds.project.toy.global.common.vo.AuthToken;
import java.util.Collections;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class AdminControllerTest extends ControllerTestSupport {

    @DisplayName(value = "관리자가 로그인 한다.")
    @Test
    void adminLogin() throws Exception {
        //given
        String id = "id";
        String password = "password";
        AdminLoginRequest request = AdminLoginRequest.of(id, password);
        AuthToken authToken = AuthToken.of("accessToken2", "refreshToken2");
        given(adminService.adminLogin(any()))
            .willReturn(authToken);
        //when then
        mockMvc.perform(
                post("/admin/login")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON).with(csrf())
            )
            .andExpect(status().isOk())
            .andExpectAll(
                jsonPath("$.accessToken").value(authToken.getAccessToken()),
                jsonPath("$.refreshToken").value(authToken.getRefreshToken())
            );
    }

    @DisplayName(value = "관리자가 카테고리 목록을 조회한다.")
    @Test
    void getCategory() throws Exception {
        //given
        GetCategoryResponse response = GetCategoryResponse.of(1L, "전자기기",
            CategoryState.ACTIVE.getName());
        //when
        given(adminService.getCategory()).willReturn(Collections.singletonList(response));
        //then
        mockMvc.perform(
                get("/admin/product/category")
            )
            .andExpect(status().isOk())
            .andExpectAll(
                jsonPath("$[0].categoryId").value(response.getCategoryId()),
                jsonPath("$[0].categoryName").value(response.getCategoryName()),
                jsonPath("$[0].categoryState").value(response.getCategoryState())
            );
    }

    @DisplayName(value = "관리자가 카테고리를 등록한다.")
    @Test
    void postCategory() throws Exception {
        //given
        PostCategoryRequest request = PostCategoryRequest.of("전자기기");
        PostCategoryResponse response = PostCategoryResponse.of(1L);
        given(adminService.postCategory(any())).willReturn(response);
        //when
        mockMvc.perform(
                post("/admin/product/category")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON).with(csrf())
            )
            .andExpect(status().isOk())
            .andExpectAll(
                jsonPath("$.categoryId").value(response.getCategoryId())
            );
        //then

    }
}