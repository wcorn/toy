package ds.project.toy;


import ds.project.toy.global.config.security.jwt.JwtTokenProvider;
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
    @Autowired
    protected PasswordEncoder passwordEncoder;
    @Autowired
    protected JwtTokenProvider jwtTokenProvider;


    @AfterEach
    void tearDown() {

    }
}
