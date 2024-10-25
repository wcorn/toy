package ds.project.toy.api.service.user;

import ds.project.toy.api.controller.user.dto.response.ChangeNicknameResponse;
import ds.project.toy.api.service.user.dto.ChangeNicknameServiceDto;
import ds.project.toy.domain.user.entity.UserInfo;
import ds.project.toy.domain.user.repository.UserInfoRepository;
import ds.project.toy.domain.user.vo.UserInfoState;
import ds.project.toy.global.common.exception.CustomException;
import ds.project.toy.global.common.exception.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserInfoRepository userInfoRepository;

    @Override
    @Transactional
    public ChangeNicknameResponse changeNickname(ChangeNicknameServiceDto serviceDto) {
        UserInfo userInfo = findUserBy(serviceDto.getUserId());
        if (userInfo.getNickname().equals(serviceDto.getNickname())) {
            throw new CustomException(ResponseCode.NICKNAME_ALREADY_IN_USE);
        }
        if (existNickname(serviceDto.getNickname())) {
            throw new CustomException(ResponseCode.DUPLICATE_NICKNAME);
        }
        userInfo.changeNickname(serviceDto.getNickname());
        return ChangeNicknameResponse.of(serviceDto.getNickname());
    }

    private boolean existNickname(String nickname) {
        return userInfoRepository.existsByNicknameAndState(nickname, UserInfoState.ACTIVE);
    }

    private UserInfo findUserBy(Long userId) {
        return userInfoRepository.findByUserIdAndState(userId, UserInfoState.ACTIVE)
            .orElseThrow(() -> new CustomException(ResponseCode.NOTFOUND_USER));
    }
}
