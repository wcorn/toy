package ds.project.toy.api.service.product;

import ds.project.toy.api.controller.product.dto.response.PostProductResponse;
import ds.project.toy.api.service.product.dto.PostProductServiceDto;
import ds.project.toy.domain.product.entity.Category;
import ds.project.toy.domain.product.entity.Product;
import ds.project.toy.domain.product.entity.ProductImage;
import ds.project.toy.domain.product.repository.CategoryRepository;
import ds.project.toy.domain.product.repository.ProductImageRepository;
import ds.project.toy.domain.product.repository.ProductRepository;
import ds.project.toy.domain.user.entity.UserInfo;
import ds.project.toy.domain.user.repository.UserInfoRepository;
import ds.project.toy.domain.user.vo.UserInfoState;
import ds.project.toy.global.common.exception.CustomException;
import ds.project.toy.global.common.exception.ResponseCode;
import ds.project.toy.global.util.MinioUtil;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final UserInfoRepository userInfoRepository;
    private final ProductImageRepository productImageRepository;
    private final MinioUtil minioUtil;

    @Transactional
    @Override
    public PostProductResponse postProduct(PostProductServiceDto dto) {
        Category category = categoryFindBy(dto.getCategoryId());
        UserInfo userInfo = userFindBy(dto.getUserId());
        Product product = productSave(Product.create(dto, userInfo, category));
        saveProductImages(product, dto.getImages());
        return PostProductResponse.of(product.getProductId());
    }

    private void saveProductImages(Product product, List<MultipartFile> images) {
        List<ProductImage> productImages = new ArrayList<>();
        String imageUrl;
        for (MultipartFile image : images) {
            imageUrl = minioUtil.uploadFile(image);
            productImages.add(ProductImage.of(product, imageUrl));
        }
        productImageRepository.saveAll(productImages);
    }

    private Product productSave(Product product) {
        return productRepository.save(product);
    }

    private Category categoryFindBy(Long categoryId) {
        return categoryRepository.findById(categoryId)
            .orElseThrow(() -> new CustomException(ResponseCode.NOT_FOUND_CATEGORY));
    }

    private UserInfo userFindBy(Long userId) {
        return userInfoRepository.findByUserIdAndState(userId, UserInfoState.ACTIVE)
            .orElseThrow(() -> new CustomException(ResponseCode.NOTFOUND_USER));
    }
}
