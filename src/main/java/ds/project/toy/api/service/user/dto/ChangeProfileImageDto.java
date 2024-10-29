package ds.project.toy.api.service.user.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class ChangeProfileImageDto {

    private Long userId;
    private MultipartFile image;

    @Builder
    private ChangeProfileImageDto(Long userId, MultipartFile image) {
        this.userId = userId;
        this.image = image;
    }

    public static ChangeProfileImageDto of(Long userId, MultipartFile image) {
        return ChangeProfileImageDto.builder()
            .userId(userId)
            .image(image)
            .build();
    }
}
