package ds.project.toy.api.controller.user.dto.response;

import ds.project.toy.domain.user.entity.UserInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetUserProfileResponse {

    private String nickname;
    private String email;
    private String profile_image;

    @Builder
    private GetUserProfileResponse(String nickname, String email, String profile_image) {
        this.nickname = nickname;
        this.email = email;
        this.profile_image = profile_image;
    }

    public static GetUserProfileResponse of(String nickname, String email, String profile_image) {
        return GetUserProfileResponse.builder()
            .nickname(nickname)
            .email(email)
            .profile_image(profile_image)
            .build();
    }

    public static GetUserProfileResponse of(UserInfo userInfo) {
        return GetUserProfileResponse.builder()
            .nickname(userInfo.getNickname())
            .email(userInfo.getEmail())
            .profile_image(userInfo.getProfileImage())
            .build();
    }
}
