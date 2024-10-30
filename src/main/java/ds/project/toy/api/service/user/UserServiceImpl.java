package ds.project.toy.api.service.user;

import ds.project.toy.api.controller.user.dto.response.ChangeNicknameResponse;
import ds.project.toy.api.controller.user.dto.response.ChangeProfileImageResponse;
import ds.project.toy.api.controller.user.dto.response.GetUserProfileResponse;
import ds.project.toy.api.service.user.dto.ChangeNicknameServiceDto;
import ds.project.toy.api.service.user.dto.ChangeProfileImageDto;
import ds.project.toy.api.service.user.dto.GetUserProfileDto;
import ds.project.toy.domain.user.entity.UserInfo;
import ds.project.toy.domain.user.repository.UserInfoRepository;
import ds.project.toy.domain.user.vo.UserInfoState;
import ds.project.toy.global.common.exception.CustomException;
import ds.project.toy.global.common.exception.ResponseCode;
import ds.project.toy.global.util.MinioUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserInfoRepository userInfoRepository;
    private final MinioUtil minioUtil;

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

    @Override
    @Transactional
    public ChangeProfileImageResponse changeProfileImage(ChangeProfileImageDto of) {
        UserInfo userInfo = findUserBy(of.getUserId());
        if (StringUtils.hasLength(userInfo.getProfileImage())) {
            minioUtil.deleteFile(userInfo.getProfileImage());
        }
        userInfo.updateProfileImage(minioUtil.uploadFile(of.getImage()));
        return ChangeProfileImageResponse.of(userInfo.getProfileImage());
    }

    @Override
    public GetUserProfileResponse getUserProfile(GetUserProfileDto serviceDto) {
        UserInfo userInfo = findUserBy(serviceDto.getUserId());
        return GetUserProfileResponse.of(userInfo);
    }

    private boolean existNickname(String nickname) {
        return userInfoRepository.existsByNicknameAndState(nickname, UserInfoState.ACTIVE);
    }

    private UserInfo findUserBy(Long userId) {
        return userInfoRepository.findByUserIdAndState(userId, UserInfoState.ACTIVE)
            .orElseThrow(() -> new CustomException(ResponseCode.NOTFOUND_USER));
    }
}
