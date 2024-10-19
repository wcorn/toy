package ds.project.toy.api.controller.auth.dto.request;

import ds.project.toy.api.service.auth.dto.ReissuedTokenServiceDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReissuedTokenRequest {

    @NotBlank(message = "access token은 필수 값입니다.")
    private String accessToken;

    @NotBlank(message = "refresh token은 필수 값입니다.")
    private String refreshToken;

    @Builder
    private ReissuedTokenRequest(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static ReissuedTokenRequest of(String accessToken, String refreshToken) {
        return ReissuedTokenRequest.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
    }

    public ReissuedTokenServiceDto toServiceDto() {
        return ReissuedTokenServiceDto.of(this.accessToken, this.refreshToken);
    }
}
