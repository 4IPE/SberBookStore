package com.example.Sber.book;

import com.example.Sber.Author.Author;
import com.example.Sber.Author.AuthorRepository;
import com.example.Sber.book.enumarated.Genre;
import com.example.Sber.book.enumarated.Language;
import com.example.Sber.sec.TestSecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Date;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = BookController.class)
@AutoConfigureMockMvc
@Import(TestSecurityConfig.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorRepository authorRepository;

    @Value("${upload.path}")
    private String uploadPath;

    private Author author;
    private Book book;

    @BeforeEach
    void setUp() {
        author = new Author();
        author.setId(1L);
        author.setName("Author");

        book = new Book();
        book.setId(1L);
        book.setName("Book");
        book.setAuthor(author);
        book.setLanguage(Language.RU);
        book.setDate(new Date(103, 1, 2));  // Обратите внимание на использование Date конструктор
        book.setDescription("Description");
        book.setGenre(Genre.COMEDIA);
        book.setPages(100);
        book.setRating(10);
        book.setBookIsNew(true);
        book.setImage("default.jpg");
        book.setPrice(100);
        book.setAmount(100L);
    }

    @Test
    void getBookDetails() throws Exception {
        when(bookService.getBookWithId(1L)).thenReturn(book);

        mvc.perform(get("/book")
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("book-details"))
                .andExpect(model().attributeExists("book"))
                .andExpect(model().attribute("book", book));
    }

    @Test
    void showFormAddBook() throws Exception {
        when(authorRepository.findAll()).thenReturn(Collections.singletonList(author));

        mvc.perform(get("/employee/add-book"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-book"))
                .andExpect(model().attributeExists("book"))
                .andExpect(model().attributeExists("languages"))
                .andExpect(model().attributeExists("genres"))
                .andExpect(model().attributeExists("authors"))
                .andExpect(model().attribute("authors", Collections.singletonList(author)));
    }

    @Test
    void addBook() throws Exception {
        MockMultipartFile file = new MockMultipartFile("image", "default.jpg", MediaType.IMAGE_JPEG_VALUE, "image content".getBytes());

        mvc.perform(multipart("/employee/add-book")
                .file(file)
                .param("name", "Book")
                .param("language", "RU")
                .param("date", "2003-02-02")
                .param("description", "Description")
                .param("genre", "COMEDIA")
                .param("pages", "100")
                .param("rating", "10")
                .param("bookIsNew", "true")
                .param("price", "100")
                .param("amount", "100"));


    }

    @Test
    void showFormAddAuthor() throws Exception {
        mvc.perform(get("/employee/add-author"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-author"));
    }

    @Test
    void addAuthor() throws Exception {
        mvc.perform(post("/employee/add-author")
                        .param("name", "Author"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/employee/add-book"));

        verify(authorRepository, times(1)).save(any(Author.class));
    }

    @Test
    void deleteBook() throws Exception {
        mvc.perform(post("/admin/book-remove")
                        .param("bookId", "1"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/admin/books"));

        verify(bookService, times(1)).deleteBook(1L);
    }

    @Test
    void getAllBooks() throws Exception {
        when(bookService.allBooks()).thenReturn(Collections.singletonList(book));

        mvc.perform(get("/admin/books"))
                .andExpect(status().isOk())
                .andExpect(view().name("booksData"))
                .andExpect(model().attributeExists("books"))
                .andExpect(model().attribute("books", Collections.singletonList(book)));
    }
}
