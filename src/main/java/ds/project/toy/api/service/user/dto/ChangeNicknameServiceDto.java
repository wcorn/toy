package ds.project.toy.api.service.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ChangeNicknameServiceDto {

    private String nickname;
    private Long userId;

    @Builder
    private ChangeNicknameServiceDto(String nickname, Long userId) {
        this.nickname = nickname;
        this.userId = userId;
    }

    public static ChangeNicknameServiceDto of(String nickname, Long userId) {
        return ChangeNicknameServiceDto.builder()
            .nickname(nickname)
            .userId(userId)
            .build();

    }
}
