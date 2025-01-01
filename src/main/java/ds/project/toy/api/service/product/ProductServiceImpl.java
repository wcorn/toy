package ds.project.toy.api.service.product;

import ds.project.toy.api.controller.product.dto.response.GetCategoryResponse;
import ds.project.toy.api.controller.product.dto.response.GetProductResponse;
import ds.project.toy.api.controller.product.dto.response.PostProductResponse;
import ds.project.toy.api.service.admin.dto.GetProductServiceDto;
import ds.project.toy.api.service.product.dto.DeleteInterestProductServiceDto;
import ds.project.toy.api.service.product.dto.PostInterestProductServiceDto;
import ds.project.toy.api.service.product.dto.PostProductServiceDto;
import ds.project.toy.domain.product.entity.Category;
import ds.project.toy.domain.product.entity.InterestProduct;
import ds.project.toy.domain.product.entity.Product;
import ds.project.toy.domain.product.entity.ProductImage;
import ds.project.toy.domain.product.repository.CategoryRepository;
import ds.project.toy.domain.product.repository.InterestProductRepository;
import ds.project.toy.domain.product.repository.ProductImageRepository;
import ds.project.toy.domain.product.repository.ProductRepository;
import ds.project.toy.domain.product.vo.CategoryState;
import ds.project.toy.domain.user.entity.UserInfo;
import ds.project.toy.domain.user.repository.UserInfoRepository;
import ds.project.toy.domain.user.vo.UserInfoState;
import ds.project.toy.global.common.exception.CustomException;
import ds.project.toy.global.common.exception.ResponseCode;
import ds.project.toy.global.common.vo.RedisPrefix;
import ds.project.toy.global.util.MinioUtil;
import ds.project.toy.global.util.RedisUtil;
import java.time.Duration;
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
    private final InterestProductRepository interestProductRepository;
    private final RedisUtil redisUtil;

    @Transactional
    @Override
    public PostProductResponse postProduct(PostProductServiceDto dto) {
        Category category = categoryFindBy(dto.getCategoryId());
        UserInfo userInfo = userFindBy(dto.getUserId());
        Product product = productSave(Product.create(dto, userInfo, category));
        saveProductImages(product, dto.getImages());
        return PostProductResponse.of(product.getProductId());
    }

    @Transactional
    @Override
    public GetProductResponse getProduct(GetProductServiceDto of) {
        Product product = productFindBy(of.getProductId());
        UserInfo userInfo = userFindBy(of.getUserId());
        if (!product.getUserInfo().getUserId().equals(userInfo.getUserId()) &&
            !redisUtil.hasKey(RedisPrefix.VIEW, String.valueOf(userInfo.getUserId()))) {
            product.viewCount();
            redisUtil.setSetWithExpire(RedisPrefix.VIEW, String.valueOf(userInfo.getUserId()),
                product.getProductId(), Duration.ofHours(6));
        }
        boolean isInterest = isInterest(product, userInfo);
        return GetProductResponse.of(product, isInterest);
    }

    @Transactional
    @Override
    public void postInterestProduct(PostInterestProductServiceDto of) {
        UserInfo userInfo = userFindBy(of.getUserId());
        Product product = productFindBy(of.getProductId());
        interestProductRepository.save(InterestProduct.of(product, userInfo));
    }

    @Transactional
    @Override
    public void deleteInterestProduct(DeleteInterestProductServiceDto of) {
        UserInfo userInfo = userFindBy(of.getUserId());
        Product product = productFindBy(of.getProductId());
        interestProductRepository.delete(interestProductFindBy(userInfo, product));
    }

    @Override
    public List<GetCategoryResponse> getCategoryList() {
        List<Category> categoryList = categoryFindAll(CategoryState.ACTIVE);
        return categoryList.stream().map(
            GetCategoryResponse::fromCategory
        ).toList();
    }

    private List<Category> categoryFindAll(CategoryState categoryState) {
        return categoryRepository.findAllByCategoryState(categoryState);
    }

    private InterestProduct interestProductFindBy(UserInfo userInfo, Product product) {
        return interestProductRepository.findByUserInfoAndProduct(userInfo, product)
            .orElseThrow(() -> new CustomException(ResponseCode.NOT_FOUND_INTEREST_PRODUCT));
    }

    private boolean isInterest(Product product, UserInfo userInfo) {
        return interestProductRepository.existsByUserInfoAndProduct(userInfo, product);
    }

    private Product productFindBy(Long productId) {
        return productRepository.findById(productId)
            .orElseThrow(() -> new CustomException(ResponseCode.NOT_FOUND_PRODUCT));
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
