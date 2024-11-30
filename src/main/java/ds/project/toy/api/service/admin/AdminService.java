package ds.project.toy.api.service.admin;

import ds.project.toy.api.controller.admin.dto.response.GetCategoryResponse;
import ds.project.toy.api.controller.admin.dto.response.PostCategoryResponse;
import ds.project.toy.api.service.admin.dto.AdminLoginServiceDto;
import ds.project.toy.api.service.admin.dto.PostCategoryServiceDto;
import ds.project.toy.global.common.vo.AuthToken;
import java.util.List;

public interface AdminService {

    AuthToken adminLogin(AdminLoginServiceDto serviceDto);

    List<GetCategoryResponse> getCategory();

    PostCategoryResponse postCategory(PostCategoryServiceDto service);
}
