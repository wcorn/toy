package ds.project.toy.api.controller.user;

import ds.project.toy.api.controller.user.dto.request.ChangeNicknameRequest;
import ds.project.toy.api.controller.user.dto.response.ChangeNicknameResponse;
import ds.project.toy.api.controller.user.dto.response.ChangeProfileImageResponse;
import ds.project.toy.api.controller.user.dto.response.GetUserProfileResponse;
import ds.project.toy.api.service.user.UserService;
import ds.project.toy.api.service.user.dto.ChangeProfileImageDto;
import ds.project.toy.api.service.user.dto.GetUserProfileDto;
import ds.project.toy.global.common.exception.CustomException;
import ds.project.toy.global.common.exception.ResponseCode;
import ds.project.toy.global.util.FileUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final FileUtil fileUtil;

    @PatchMapping("/nickname")
    public ResponseEntity<ChangeNicknameResponse> changeNickname(
        @Valid @RequestBody ChangeNicknameRequest request) {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        Long memberId = Long.parseLong(loggedInUser.getName());
        return ResponseEntity.ok(userService.changeNickname(request.toServiceDto(memberId)));
    }

    @PatchMapping(value = "/profile-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ChangeProfileImageResponse> changeProfileImage(
        @RequestPart(value = "image") MultipartFile image) {
        if (!fileUtil.isImageFile(image)) {
            throw new CustomException(ResponseCode.NOT_IMAGE);
        }
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.parseLong(loggedInUser.getName());
        return ResponseEntity.ok(
            userService.changeProfileImage(ChangeProfileImageDto.of(userId, image)));
    }

    @GetMapping(value = "/profile")
    public ResponseEntity<GetUserProfileResponse> getUserProfile() {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.parseLong(loggedInUser.getName());
        return ResponseEntity.ok(userService.getUserProfile(GetUserProfileDto.of(userId)));
    }
}
