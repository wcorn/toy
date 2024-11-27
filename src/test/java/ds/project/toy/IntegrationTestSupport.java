package ds.project.toy;


import ds.project.toy.api.service.admin.AdminService;
import ds.project.toy.api.service.auth.AuthService;
import ds.project.toy.api.service.product.ProductService;
import ds.project.toy.api.service.user.UserService;
import ds.project.toy.domain.product.repository.CategoryRepository;
import ds.project.toy.domain.product.repository.InterestProductRepository;
import ds.project.toy.domain.product.repository.ProductImageRepository;
import ds.project.toy.domain.product.repository.ProductRepository;
import ds.project.toy.domain.user.repository.AdminLoginRepository;
import ds.project.toy.domain.user.repository.SocialLoginRepository;
import ds.project.toy.domain.user.repository.SocialProviderRepository;
import ds.project.toy.domain.user.repository.UserInfoRepository;
import ds.project.toy.global.config.security.jwt.JwtTokenProvider;
import ds.project.toy.global.util.FileUtil;
import ds.project.toy.global.util.MinioUtil;
import ds.project.toy.global.util.RedisUtil;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public abstract class IntegrationTestSupport {

    @MockBean
    protected RedisUtil redisUtil;
    @MockBean
    protected MinioUtil minioUtil;
    @Autowired
    protected FileUtil fileUtil;

    @Autowired
    protected PasswordEncoder passwordEncoder;
    @Autowired
    protected JwtTokenProvider jwtTokenProvider;

    @Autowired
    protected AuthService authService;
    @Autowired
    protected UserService userService;
    @Autowired
    protected AdminService adminService;
    @Autowired
    protected ProductService productService;

    @Autowired
    protected AdminLoginRepository adminLoginRepository;
    @Autowired
    protected SocialLoginRepository socialLoginRepository;
    @Autowired
    protected SocialProviderRepository socialProviderRepository;
    @Autowired
    protected UserInfoRepository userInfoRepository;
    @Autowired
    protected ProductRepository productRepository;
    @Autowired
    protected CategoryRepository categoryRepository;
    @Autowired
    protected InterestProductRepository interestProductRepository;
    @Autowired
    protected ProductImageRepository productImageRepository;

    @AfterEach
    void tearDown() {
        productImageRepository.deleteAllInBatch();
        interestProductRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        categoryRepository.deleteAllInBatch();
        adminLoginRepository.deleteAllInBatch();
        socialLoginRepository.deleteAllInBatch();
        socialProviderRepository.deleteAllInBatch();
        userInfoRepository.deleteAllInBatch();
    }
}
