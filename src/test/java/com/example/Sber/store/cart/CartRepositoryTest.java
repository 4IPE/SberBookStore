package com.example.Sber.store.cart;

import com.example.Sber.Author.Author;
import com.example.Sber.Author.AuthorRepository;
import com.example.Sber.Store.cart.CartItem;
import com.example.Sber.Store.cart.CartRepository;
import com.example.Sber.book.Book;
import com.example.Sber.book.BookRepository;
import com.example.Sber.book.enumarated.Genre;
import com.example.Sber.book.enumarated.Language;
import com.example.Sber.user.User;
import com.example.Sber.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@TestPropertySource(properties = {"db.name=test"})
public class CartRepositoryTest {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;

    private Author author;
    private Book book;
    private CartItem cartItem;
    private User user;

    @BeforeEach
    void setUp() {
        this.author = createAuthor("Author");
        Date date = new Date(2003, 02, 02, 00, 00);
        this.book = createBook("Book", Language.RU, date, "Desc", Genre.COMEDIA, 100
                , 10, true, "default.jpg", 100, 100L);
        this.user = createUser();
        this.cartItem = createCartItem();
    }

    private Author createAuthor(String name) {
        Author author = new Author();
        author.setName(name);
        return authorRepository.save(author);
    }

    private Book createBook(String name, Language lang, Date date, String desc,
                            Genre genre, Integer pages, Integer rating, Boolean isNew,
                            String path, Integer price, Long amount) {
        Book createBook = new Book();
        createBook.setName(name);
        createBook.setAuthor(author);
        createBook.setLanguage(lang);
        createBook.setDate(date);
        createBook.setDescription(desc);
        createBook.setGenre(genre);
        createBook.setPages(pages);
        createBook.setRating(rating);
        createBook.setBookIsNew(isNew);
        createBook.setImage(path);
        createBook.setPrice(price);
        createBook.setAmount(amount);
        return bookRepository.save(createBook);
    }

    private CartItem createCartItem() {
        CartItem cartItem1 = new CartItem();
        cartItem1.setBook(book);
        cartItem1.setUser(user);
        cartItem1.setQuantity(10L);
        return cartRepository.save(cartItem1);
    }

    private User createUser() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("password");

        return userRepository.save(user);
    }

    @Test
    void findByIdAndUserIdTest() {
        CartItem item = cartRepository.findByIdAndUserId(cartItem.getId(), user.getId());
        assertThat(item)
                .isNotNull()
                .isEqualTo(cartItem);
    }

    @Test
    void findByUserIdTest() {
        List<CartItem> items = cartRepository.findByUserId(user.getId());
        List<CartItem> itemsExp = Collections.singletonList(cartItem);
        assertThat(items)
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(itemsExp);
    }
}
