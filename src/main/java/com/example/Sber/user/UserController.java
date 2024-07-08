package com.example.Sber.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/profile")
    public String showProfileForm(Model model) {
        model.addAttribute("user", userService.getCurrentUser());
        return "profile";
    }

    @GetMapping("/admin/users")
    public String userData(Model model) {
        model.addAttribute("users", userService.getAllUsers());
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

    @PostMapping("/admin/add-role")
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
    public String registerUser(@ModelAttribute User user) {
        userService.addUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

}
