package ds.project.toy.api.service.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GetUserProfileDto {

    private Long userId;

    @Builder
    private GetUserProfileDto(Long userId) {
        this.userId = userId;
    }

    public static GetUserProfileDto of(Long userId) {
        return GetUserProfileDto.builder()
            .userId(userId)
            .build();
    }
}
