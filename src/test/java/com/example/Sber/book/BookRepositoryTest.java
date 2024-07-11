package com.example.Sber.book;

import com.example.Sber.Author.Author;
import com.example.Sber.Author.AuthorRepository;
import com.example.Sber.book.enumarated.Genre;
import com.example.Sber.book.enumarated.Language;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(properties = {"db.name=test"})
public class BookRepositoryTest {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;

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

    @Test
    void findByNameTest() {
        Book findBook = bookRepository.findByName("Book");
        assertThat(findBook).isNotNull().isEqualTo(this.book);
    }

    @Test
    void findByNameContainingIgnoreCaseTest() {
        List<Book> findBooks = bookRepository.findByNameContainingIgnoreCase("bOOk");
        assertThat(findBooks).isNotNull();
        assertThat(findBooks.contains(this.book)).isTrue();
    }
}
