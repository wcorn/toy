package ds.project.toy.api.controller.user;

import ds.project.toy.api.controller.user.dto.request.ChangeNicknameRequest;
import ds.project.toy.api.controller.user.dto.response.ChangeNicknameResponse;
import ds.project.toy.api.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PatchMapping("/nickname")
    public ResponseEntity<ChangeNicknameResponse> changeNickname(@Valid @RequestBody ChangeNicknameRequest request) {
        //todo jwt filter 개발 후 token에서 user 정보 가져오기
        return ResponseEntity.ok(userService.changeNickname(request.toServiceDto(1L)));
    }
}
