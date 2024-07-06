package com.example.Sber.cart;

import com.example.Sber.book.Book;
import com.example.Sber.book.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
@Slf4j
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private BookRepository bookRepository;

    @GetMapping
    public String showCart(Model model) {
        model.addAttribute("carts", cartService.allCartItemsUser());
        ResponseEntity.ok();
        log.info("Выполнен запрос к методу showCart");
        return "cart";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam Integer bookId, @RequestParam Long quantity) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Invalid book ID"));
        cartService.addItem(book, quantity);
        ResponseEntity.ok();
        log.info("Выполнен запрос к методу addToCart");
        return "redirect:/cart";
    }

    @PostMapping("/remove")
    public String removeFromCart(@RequestParam Long cartId) {
        cartService.removeItem(cartId);
        ResponseEntity.noContent().build();
        log.info("Выполнен запрос к методу removeFromCart");
        return "redirect:/cart";
    }
}
