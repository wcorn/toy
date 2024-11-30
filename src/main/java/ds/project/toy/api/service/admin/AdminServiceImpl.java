package ds.project.toy.api.service.admin;

import ds.project.toy.api.controller.admin.dto.response.GetCategoryResponse;
import ds.project.toy.api.controller.admin.dto.response.PostCategoryResponse;
import ds.project.toy.api.service.admin.dto.AdminLoginServiceDto;
import ds.project.toy.api.service.admin.dto.PostCategoryServiceDto;
import ds.project.toy.domain.product.entity.Category;
import ds.project.toy.domain.product.repository.CategoryRepository;
import ds.project.toy.domain.user.entity.AdminLogin;
import ds.project.toy.domain.user.repository.AdminLoginRepository;
import ds.project.toy.global.common.exception.CustomException;
import ds.project.toy.global.common.exception.ResponseCode;
import ds.project.toy.global.common.vo.AuthToken;
import ds.project.toy.global.config.security.jwt.JwtTokenProvider;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminServiceImpl implements AdminService {

    private final PasswordEncoder passwordEncoder;
    private final AdminLoginRepository adminLoginRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final CategoryRepository categoryRepository;

    @Override
    public AuthToken adminLogin(AdminLoginServiceDto serviceDto) {
        AdminLogin adminLogin = adminLoginFindBy(serviceDto.getId());
        if (!checkPassword(adminLogin, serviceDto.getPassword())) {
            throw new CustomException(ResponseCode.FAILED_ADMIN_LOGIN);
        }

        return jwtTokenProvider.createTokenAndStore(String.valueOf(adminLogin.getAdminId()));
    }

    @Override
    public List<GetCategoryResponse> getCategory() {
        return getCategories().stream().map(
            GetCategoryResponse::fromCategory
        ).toList();
    }

    @Transactional
    @Override
    public PostCategoryResponse postCategory(PostCategoryServiceDto dto) {
        return PostCategoryResponse.of(
            categoryRepository.save(Category.create(dto.getContent())).getCategoryId());
    }

    private List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    private boolean checkPassword(AdminLogin adminLogin, String password) {
        return passwordEncoder.matches(
            createSaltedPassword(password, adminLogin.getSalt()), adminLogin.getPassword()
        );
    }

    private String createSaltedPassword(String password, String salt) {
        return password + salt;
    }

    private AdminLogin adminLoginFindBy(String id) {
        return adminLoginRepository.findById(id)
            .orElseThrow(() -> new CustomException(ResponseCode.FAILED_ADMIN_LOGIN));
    }
}
