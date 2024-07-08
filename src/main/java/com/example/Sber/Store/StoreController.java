package com.example.Sber.Store;


import com.example.Sber.book.BookService;
import com.example.Sber.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping
@RequiredArgsConstructor
public class StoreController {
    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String mainPage(Model model) {
        model.addAttribute("user", userService.getCurrentUser());
        model.addAttribute("books", bookService.allBooks());
        return "home";
    }

}
