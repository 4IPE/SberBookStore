package com.example.Sber.book;


import com.example.Sber.Author.Author;
import com.example.Sber.book.enumarated.Genre;
import com.example.Sber.book.enumarated.Language;
import com.example.Sber.sec.TestSecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Date;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BookRestController.class)
@AutoConfigureMockMvc
@Import(TestSecurityConfig.class)
public class BookRestControllerTest {
    @MockBean
    private BookService bookService;
    @Autowired
    private MockMvc mvc;
    private Author author;
    private Book book;

    @BeforeEach
    void setUp() {
        this.author = createAuthor("Author");
        Date date = new Date(2003, 02, 02, 00, 00);
        this.book = createBook("Book", Language.RU, date, "Desc", Genre.COMEDIA, 100
                , 10, true, "default.jpg", 100, 100L);
    }

    private Author createAuthor(String name) {
        Author author = new Author();
        author.setName(name);
        return author;
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
        return createBook;
    }

    @Test
    void searchTest() throws Exception {
        when(bookService.search(anyString()))
                .thenReturn(Collections.emptyList());
        mvc.perform(get("/searchBooks")
                        .param("query", book.getName()))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}
