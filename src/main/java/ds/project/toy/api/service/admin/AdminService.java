package ds.project.toy.api.service.admin;

import ds.project.toy.api.service.admin.dto.AdminLoginServiceDto;
import ds.project.toy.global.common.vo.AuthToken;

public interface AdminService {

    AuthToken adminLogin(AdminLoginServiceDto serviceDto);
}
