package com.example.Sber.user;

import com.example.Sber.Author.Author;
import com.example.Sber.Author.AuthorRepository;
import com.example.Sber.book.Book;
import com.example.Sber.book.enumarated.Genre;
import com.example.Sber.book.enumarated.Language;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/profile")
    public String showProfileForm(Model model) {
        model.addAttribute("user", userService.getCurrentUser());
        return "profile";
    }

    @GetMapping("/admin")
    public String userData(Model model) {
        model.addAttribute("users",userService.getAllUsers());
        model.addAttribute("roles",roleRepository.findAll());
        return "usersData" ;
    }
    @PostMapping("/admin/remove")
    public String userRemove(@RequestParam Long userId ) {
        userService.delUser(userId);
        return "redirect:/admin" ;
    }
    @PostMapping("/admin/add-role")
    public String userUpdRole(@RequestParam Long roleId,@RequestParam Long userId) {
        userService.userUpdRole(userId,roleId);
        return "redirect:/admin" ;
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


    @GetMapping("/employee/add-book")
    public String showFormAddBook(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("languages", Language.values());
        model.addAttribute("genres", Genre.values());
        List<Author> authors = authorRepository.findAll() == null ? new ArrayList<>() : authorRepository.findAll();
        model.addAttribute("authors", authors);
        return "add-book";
    }

    @PostMapping("/employee/add-book")
    public String addBook(@ModelAttribute Book book) {
        Book bookSave= userService.addBook(book);
        return "redirect:/";
    }

    @GetMapping("/employee/add-author")
    public String showFormAddAuthor(Model model) {
        return "add-author";
    }

    @PostMapping("/employee/add-author")
    public String addAuthor(@ModelAttribute Author author) {
        authorRepository.save(author);
        return "redirect:/employee/add-book";
    }

    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Author.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                Author author = authorRepository.findById(Long.valueOf(text)).orElse(null);
                setValue(author);
            }
        });
    }

}
