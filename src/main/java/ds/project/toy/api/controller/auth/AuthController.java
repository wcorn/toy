package ds.project.toy.api.controller.auth;

import ds.project.toy.api.controller.auth.dto.request.ReissuedTokenRequest;
import ds.project.toy.api.service.auth.AuthService;
import ds.project.toy.global.common.vo.AuthToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/token/reissue")
    public ResponseEntity<AuthToken> reissuedToken(@RequestBody ReissuedTokenRequest request) {
        return ResponseEntity.ok(authService.reissuedToken(request.toServiceDto()));
    }
}
