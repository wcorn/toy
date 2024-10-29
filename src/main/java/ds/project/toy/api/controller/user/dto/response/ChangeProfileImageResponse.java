package ds.project.toy.api.controller.user.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChangeProfileImageResponse {

    private String profileImage;

    @Builder
    private ChangeProfileImageResponse(String profileImage) {
        this.profileImage = profileImage;
    }

    public static ChangeProfileImageResponse of(String profileImage) {
        return ChangeProfileImageResponse.builder()
            .profileImage(profileImage)
            .build();
    }
}
