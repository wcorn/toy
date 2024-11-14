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
    private String profileImage;

    @Builder
    private GetUserProfileResponse(String nickname, String email, String profileImage) {
        this.nickname = nickname;
        this.email = email;
        this.profileImage = profileImage;
    }

    public static GetUserProfileResponse of(String nickname, String email, String profileImage) {
        return GetUserProfileResponse.builder()
            .nickname(nickname)
            .email(email)
            .profileImage(profileImage)
            .build();
    }

    public static GetUserProfileResponse of(UserInfo userInfo) {
        return GetUserProfileResponse.builder()
            .nickname(userInfo.getNickname())
            .email(userInfo.getEmail())
            .profileImage(userInfo.getProfileImage())
            .build();
    }
}
