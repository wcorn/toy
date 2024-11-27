package ds.project.toy.api.controller.product.dto.request;

import ds.project.toy.api.service.product.dto.PostProductServiceDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
public class PostProductRequest {

    @Schema(description = "제품 판매글의 제목 (최대 64자)", example = "제품 제목")
    @NotBlank(message = "제목을 입력해주세요")
    @Size(message = "제목은 64자를 넘을 수 없습니다.", max = 64)
    private String title;
    @Schema(description = "제품 판매글의 내용 (최대 4096자)", example = "제품 내용")
    @NotBlank(message = "내용을 입력해주세요")
    @Size(message = "내용은 4096자를 넘을 수 없습니다.", max = 4096)
    private String content;
    @Schema(description = "제품 가격", example = "10000")
    private Long price;
    @Schema(description = "제품 카테고리 ID", example = "1")
    private Long categoryId;

    @Builder
    private PostProductRequest(String title, String content, Long price, Long categoryId) {
        this.title = title;
        this.content = content;
        this.price = price;
        this.categoryId = categoryId;
    }

    public static PostProductRequest of(String title, String content, Long price, Long categoryId) {
        return PostProductRequest.builder()
            .title(title)
            .categoryId(categoryId)
            .content(content)
            .price(price).build();
    }

    public PostProductServiceDto toService(Long userId, List<MultipartFile> files) {
        return PostProductServiceDto.of(userId, title, content, price, categoryId, files);
    }
}
