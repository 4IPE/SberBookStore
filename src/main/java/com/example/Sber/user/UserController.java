package com.example.Sber.user;

import com.example.Sber.sec.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/profile")
    public String showProfileForm(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
        try {
            model.addAttribute("user", customUserDetails.getUser());
        } catch (NullPointerException ex) {
            model.addAttribute("user", null);
        }
        return "profile";
    }

    @GetMapping("/admin/users")
    public String userData(Model model) {
        List<User> userList = userService.getAllUsers();
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("roles", roleRepository.findAll());
        return "usersData";
    }

    @GetMapping("/admin")
    public String showAdminPanel(Model model) {
        return "admin";
    }

    @PostMapping("/admin/remove")
    public String userRemove(@RequestParam Long userId) {
        userService.delUser(userId);
        return "redirect:/admin";
    }

    @PostMapping("/admin/update-role")
    public String userUpdRole(@RequestParam Long roleId, @RequestParam Long userId) {
        userService.userUpdRole(userId, roleId);
        return "redirect:/admin";
    }

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(User user, Model model) {
        if (userService.isUsernameTaken(user.getUsername())) {
            model.addAttribute("usernameError", "Username already taken");
            return "registration";
        }
        userService.addUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

}
