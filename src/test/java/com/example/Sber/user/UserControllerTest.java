package com.example.Sber.user;

import com.example.Sber.sec.TestSecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@SpringJUnitConfig
@Import(TestSecurityConfig.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserController userController;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }


    @Test
    public void testShowAdminPanel() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin"));
    }

    @Test
    public void testUserRemove() throws Exception {
        Long userId = 1L;
        doNothing().when(userService).delUser(userId);

        mockMvc.perform(post("/admin/remove").param("userId", String.valueOf(userId)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin"));
    }


    @Test
    public void testShowRegistrationForm() throws Exception {
        mockMvc.perform(get("/registration"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(view().name("registration"));
    }


    @Test
    public void testRegisterUserWithExistingUsername() throws Exception {
        User user = new User();
        user.setUsername("testUser");

        when(userService.isUsernameTaken(user.getUsername())).thenReturn(true);

        mockMvc.perform(post("/registration")
                        .param("username", user.getUsername())
                        .param("password", "password"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("usernameError"))
                .andExpect(view().name("registration"));
    }

    @Test
    public void testShowLoginForm() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }
}
