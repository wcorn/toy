package ds.project.toy.api.controller.admin;

import ds.project.toy.api.controller.admin.dto.request.AdminLoginRequest;
import ds.project.toy.api.service.admin.AdminService;
import ds.project.toy.global.common.vo.AuthToken;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
