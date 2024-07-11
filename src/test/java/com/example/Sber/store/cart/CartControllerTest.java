package com.example.Sber.store.cart;

import com.example.Sber.Store.cart.CartController;
import com.example.Sber.Store.cart.CartItem;
import com.example.Sber.Store.cart.CartService;
import com.example.Sber.book.Book;
import com.example.Sber.book.BookRepository;
import com.example.Sber.sec.TestSecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CartController.class)
@AutoConfigureMockMvc
@Import(TestSecurityConfig.class)
public class CartControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CartService cartService;

    @MockBean
    private BookRepository bookRepository;

    private Book book;
    private CartItem cartItem;

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setId(1L);
        book.setName("Test Book");

        cartItem = new CartItem();
        cartItem.setId(1L);
        cartItem.setBook(book);
        cartItem.setQuantity(1L);
    }

    @Test
    void showCartTest() throws Exception {
        when(cartService.allCartItemsUser()).thenReturn(Collections.emptyList());

        mvc.perform(get("/cart"))
                .andExpect(status().isOk())
                .andExpect(view().name("cart"))
                .andExpect(model().attributeExists("carts"))
                .andExpect(model().attribute("carts", Collections.emptyList()));

        verify(cartService, times(1)).allCartItemsUser();
    }

    @Test
    void addToCartShouldAddItemToCartAndRedirect() throws Exception {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        mvc.perform(post("/cart/add")
                        .param("bookId", "1")
                        .param("quantity", "1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/cart"));

        verify(bookRepository, times(1)).findById(1L);
        verify(cartService, times(1)).addItem(book, 1L);
    }

    @Test
    void addToCartShouldThrowExceptionForNotFound() throws Exception {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        mvc.perform(post("/cart/add")
                        .param("bookId", "1")
                        .param("quantity", "1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isNotFound());

        verify(bookRepository, times(1)).findById(1L);
        verify(cartService, times(0)).addItem(any(Book.class), anyLong());
    }

    @Test
    void removeFromCartTest() throws Exception {
        mvc.perform(post("/cart/remove")
                        .param("cartId", "1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/cart"));

        verify(cartService, times(1)).removeItem(1L);
    }
}
