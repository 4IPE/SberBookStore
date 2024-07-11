package com.example.Sber.user;

import com.example.Sber.sec.TestSecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@SpringJUnitConfig
@Import(TestSecurityConfig.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddUser() {
        User user = new User();
        user.setPassword("plainPassword");

        Role role = new Role();
        role.setName("user");

        when(passwordEncoder.encode(any(String.class))).thenReturn("encodedPassword");
        when(roleRepository.findByName("user")).thenReturn(role);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = userService.addUser(user);

        assertNotNull(savedUser);
        assertEquals("encodedPassword", savedUser.getPassword());
        assertEquals("user", savedUser.getRole().getName());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testIsUsernameTaken() {
        String username = "testUser";
        when(userRepository.findByUsername(username)).thenReturn(new User());

        boolean isTaken = userService.isUsernameTaken(username);

        assertTrue(isTaken);
    }

    @Test
    public void testGetAllUsers() {
        List<User> users = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(users);

        List<User> allUsers = userService.getAllUsers();

        assertNotNull(allUsers);
        assertEquals(users, allUsers);
    }

    @Test
    public void testDelUser() {
        Long userId = 1L;
        doNothing().when(userRepository).deleteById(userId);

        userService.delUser(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    public void testUserUpdRole() {
        Long userId = 1L;
        Long roleId = 1L;

        User user = new User();
        Role role = new Role();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(roleRepository.findById(roleId)).thenReturn(Optional.of(role));
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.userUpdRole(userId, roleId);

        assertEquals(role, user.getRole());
        verify(userRepository, times(1)).save(user);
    }


    @Test
    public void testGetUserByUserName() {
        String username = "testUser";
        User user = new User();
        when(userRepository.findByUsername(username)).thenReturn(user);

        User foundUser = userService.getUserByUserName(username);

        assertNotNull(foundUser);
        assertEquals(user, foundUser);
    }
}
