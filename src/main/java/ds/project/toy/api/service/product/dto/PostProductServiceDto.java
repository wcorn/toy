package ds.project.toy.api.service.product.dto;

import ds.project.toy.api.controller.product.dto.request.PostProductRequest;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class PostProductServiceDto {

    private Long userId;
    private String title;
    private String content;
    private Long price;
    private Long categoryId;
    private List<MultipartFile> images;

    @Builder
    public PostProductServiceDto(Long userId, String title, String content, Long price, Long categoryId,
        List<MultipartFile> images) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.price = price;
        this.categoryId = categoryId;
        this.images = images;
    }

    public static PostProductServiceDto of(Long userId, String title, String content, Long price,
        Long categoryId, List<MultipartFile> images) {
        return PostProductServiceDto.builder()
            .userId(userId)
            .title(title)
            .content(content)
            .price(price)
            .categoryId(categoryId)
            .images(images)
            .build();
    }
}
