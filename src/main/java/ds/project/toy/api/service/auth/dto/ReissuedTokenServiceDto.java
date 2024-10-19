package ds.project.toy.api.service.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ReissuedTokenServiceDto {

    private String accessToken;
    private String refreshToken;

    @Builder
    private ReissuedTokenServiceDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static ReissuedTokenServiceDto of(String accessToken, String refreshToken) {
        return ReissuedTokenServiceDto.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
    }
}
