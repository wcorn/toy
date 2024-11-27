package ds.project.toy.api.controller.product;

import ds.project.toy.api.controller.product.dto.request.PostProductRequest;
import ds.project.toy.api.controller.product.dto.response.PostProductResponse;
import ds.project.toy.api.service.product.ProductService;
import ds.project.toy.global.common.exception.CustomException;
import ds.project.toy.global.common.exception.ResponseCode;
import ds.project.toy.global.config.openapi.SwaggerBody;
import ds.project.toy.global.util.FileUtil;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Encoding;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final FileUtil fileUtil;

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @SwaggerBody(content = @Content(
        encoding = @Encoding(name = "data", contentType = MediaType.APPLICATION_JSON_VALUE)))
    public ResponseEntity<PostProductResponse> postProduct(
        @RequestPart(value = "data") @Valid PostProductRequest postProductRequest,
        @RequestPart(value = "image", required = false) List<MultipartFile> files) {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.parseLong(loggedInUser.getName());
        if (files == null) {
            files = new ArrayList<>();
        }
        if (files.size() > 10) {
            throw new CustomException(ResponseCode.MAX_IMAGE_COUNT_EXCEEDED);
        }
            for (MultipartFile file : files) {
                if (!fileUtil.isImageFile(file)) {
                    throw new CustomException(ResponseCode.NOT_IMAGE);
                }
            }
        return ResponseEntity.ok(productService.postProduct(
            postProductRequest.toService(userId, files)));
    }
}
