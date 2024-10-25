package ds.project.toy.api.controller.user.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChangeNicknameResponse {

    private String nickname;

    @Builder
    private ChangeNicknameResponse(String nickname) {
        this.nickname = nickname;
    }

    public static ChangeNicknameResponse of(String nickname) {
        return ChangeNicknameResponse.builder()
            .nickname(nickname)
            .build();
    }

}
