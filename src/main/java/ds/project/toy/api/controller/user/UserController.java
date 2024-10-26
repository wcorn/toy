package ds.project.toy.api.controller.user;

import ds.project.toy.api.controller.user.dto.request.ChangeNicknameRequest;
import ds.project.toy.api.controller.user.dto.response.ChangeNicknameResponse;
import ds.project.toy.api.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ResponseEntity<ChangeNicknameResponse> changeNickname(
        @Valid @RequestBody ChangeNicknameRequest request) {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        Long memberId = Long.parseLong(loggedInUser.getName());
        return ResponseEntity.ok(userService.changeNickname(request.toServiceDto(memberId)));
    }
}
