package ds.project.toy.api.service.user;

import ds.project.toy.api.controller.user.dto.response.ChangeNicknameResponse;
import ds.project.toy.api.service.user.dto.ChangeNicknameServiceDto;

public interface UserService {

    ChangeNicknameResponse changeNickname(ChangeNicknameServiceDto serviceDto);
}
