package com.example.Sber.Store;


import com.example.Sber.book.BookService;
import com.example.Sber.sec.CustomUserDetails;
import com.example.Sber.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public String mainPage(@AuthenticationPrincipal CustomUserDetails user, Model model) {
        if(user==null){
            model.addAttribute("books", bookService.allBooks());
            return "home-dont-login";
        }
        model.addAttribute("user",user.getUser());
        model.addAttribute("books", bookService.allBooks());
        return "home";
    }

}
