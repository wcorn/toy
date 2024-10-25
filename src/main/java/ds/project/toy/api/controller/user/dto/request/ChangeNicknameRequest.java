package ds.project.toy.api.controller.user.dto.request;

import ds.project.toy.api.service.user.dto.ChangeNicknameServiceDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChangeNicknameRequest {

    @NotBlank(message = "닉네임을 입력해주세요")
    @Size(max = 12, min = 2, message = "닉네임은 최소 2글자, 최대 12글자입니다.")
    @Pattern(regexp = "^[가-힣a-zA-Z0-9]+$", message = "닉네임은 띄어쓰기 없이 한글, 영문, 숫자만 가능합니다.")
    private String nickname;

    @Builder
    private ChangeNicknameRequest(String nickname) {
        this.nickname = nickname;
    }

    public static ChangeNicknameRequest of(String nickname) {
        return ChangeNicknameRequest.builder()
            .nickname(nickname)
            .build();
    }

    public ChangeNicknameServiceDto toServiceDto(Long userId) {
        return ChangeNicknameServiceDto.of(this.nickname, userId);
    }
}
