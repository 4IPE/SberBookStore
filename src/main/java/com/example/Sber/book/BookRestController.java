package com.example.Sber.book;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookRestController {
    @Autowired
    private BookService bookService;

    @GetMapping("/searchBooks")
    @ResponseBody
    public List<Book> searchBooks(@RequestParam("query") String query) {
        return bookService.search(query);
    }
}
