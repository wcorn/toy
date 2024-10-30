package ds.project.toy.api.service.user;

import ds.project.toy.api.controller.user.dto.response.ChangeNicknameResponse;
import ds.project.toy.api.controller.user.dto.response.ChangeProfileImageResponse;
import ds.project.toy.api.controller.user.dto.response.GetUserProfileResponse;
import ds.project.toy.api.service.user.dto.ChangeNicknameServiceDto;
import ds.project.toy.api.service.user.dto.ChangeProfileImageDto;
import ds.project.toy.api.service.user.dto.GetUserProfileDto;

public interface UserService {

    ChangeNicknameResponse changeNickname(ChangeNicknameServiceDto serviceDto);

    ChangeProfileImageResponse changeProfileImage(ChangeProfileImageDto of);

    GetUserProfileResponse getUserProfile(GetUserProfileDto of);
}
