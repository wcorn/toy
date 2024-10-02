package ds.project.toy.api.auth.controller;

import ds.project.toy.api.auth.controller.dto.response.SocialLoginCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "[사용자] 인증, 인가 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    /**
     * 소셜 로그인 인가코드 받는 API ( redirect 용 이고, 실제 사용할 일은 없으므로 스웨거에서 숨김 처리)
     *
     * @param code 인가코드 받는 파라미터
     * @return 받은 인가코드 반환
     */
    @Operation(hidden = true)
    @GetMapping(value = "/login/social/code")
    public ResponseEntity<SocialLoginCode> getAuthorizationCode(@RequestParam String code) {
        return ResponseEntity.ok(SocialLoginCode.of(code));
    }
}
