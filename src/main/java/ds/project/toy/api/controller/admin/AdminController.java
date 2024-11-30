package ds.project.toy.api.controller.admin;

import ds.project.toy.api.controller.admin.dto.request.AdminLoginRequest;
import ds.project.toy.api.controller.admin.dto.request.PostCategoryRequest;
import ds.project.toy.api.controller.admin.dto.response.GetCategoryResponse;
import ds.project.toy.api.controller.admin.dto.response.PostCategoryResponse;
import ds.project.toy.api.service.admin.AdminService;
import ds.project.toy.global.common.vo.AuthToken;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/login")
    public ResponseEntity<AuthToken> adminLogin(
        @Valid @RequestBody AdminLoginRequest request) {
        return ResponseEntity.ok(adminService.adminLogin(request.toServiceDto()));
    }

    @GetMapping("/product/category")
    public ResponseEntity<List<GetCategoryResponse>> getCategory() {
        return ResponseEntity.ok(adminService.getCategory());
    }
    @PostMapping("/product/category")
    public ResponseEntity<PostCategoryResponse> postCategory(
        @Valid @RequestBody PostCategoryRequest request
    ) {
        return ResponseEntity.ok(adminService.postCategory(request.toService()));
    }
}
