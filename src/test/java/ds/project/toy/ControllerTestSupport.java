package ds.project.toy;


import com.fasterxml.jackson.databind.ObjectMapper;
import ds.project.toy.api.controller.auth.AuthController;
import ds.project.toy.api.controller.user.UserController;
import ds.project.toy.api.service.auth.AuthService;
import ds.project.toy.api.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {
    AuthController.class,
    UserController.class,
})
@WithMockUser
public abstract class ControllerTestSupport {

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected AuthService authService;
    @MockBean
    protected UserService userService;
}
