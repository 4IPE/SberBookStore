package com.example.Sber.Store;

import com.example.Sber.book.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping
@RequiredArgsConstructor
public class StoreController {
    @Autowired
    private StoreService storeService;

    @GetMapping("/")
    public String mainPage(Model model) {
        model.addAttribute("user",storeService.getCurrentUser());
       model.addAttribute("books",storeService.getAllBook());
        return "home" ;
    }

}
