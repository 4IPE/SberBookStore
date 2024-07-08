package com.example.Sber.user;

import com.example.Sber.exception.NotFound;
import com.example.Sber.sec.CustomUserDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public User addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(roleRepository.findByName("user"));
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll() != null ? userRepository.findAll() : new ArrayList<>();
    }

    @Override
    @Transactional
    public void delUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void userUpdRole(Long userId, Long roleId) {
        User user = userRepository.findById(userId).orElseThrow(NotFound::new);
        Role role = roleRepository.findById(roleId).orElseThrow(NotFound::new);
        user.setRole(role);
        userRepository.save(user);
    }

    public User getCurrentUser() {
        return userRepository.findById(getCurrentUserId()).orElseThrow(NotFound::new);
    }

    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof CustomUserDetails) {
                CustomUserDetails userDetails = (CustomUserDetails) principal;
                return userDetails.getUser().getId();
            } else {
                throw new IllegalArgumentException("Principal is not an instance of CustomUserDetails");
            }
        }
        throw new IllegalArgumentException("User not found in Security Context");
    }
}
