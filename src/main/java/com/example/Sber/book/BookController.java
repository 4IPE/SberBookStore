package com.example.Sber.book;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/book")
@RequiredArgsConstructor
public class BookController {
    @Autowired
    private BookService bookService;

@GetMapping
public String getBookDetails(@RequestParam(name = "id") Integer id, Model model){
    model.addAttribute("book",bookService.getBookWithId(id));
    return "book-details";
}


}
