package com.example.Sber.book;

import com.example.Sber.Author.Author;
import com.example.Sber.book.enumarated.Genre;
import com.example.Sber.book.enumarated.Language;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BookMapperTest {
    private final BookMapper bookMapper;
    private BookInDto bookInDto;
    private Author author;

    @BeforeEach
    void setUp() {
        this.author = createAuthor("Author");
        Date date = new Date(2003, 02, 02, 00, 00);
        this.bookInDto = createBook("Book", Language.RU, date, "Desc", Genre.COMEDIA, 100
                , 10, true, 100, 100L);
    }

    private Author createAuthor(String name) {
        Author author = new Author();
        author.setName(name);
        return author;
    }

    private BookInDto createBook(String name, Language lang, Date date, String desc,
                                 Genre genre, Integer pages, Integer rating, Boolean isNew,
                                 Integer price, Long amount) {
        BookInDto createBook = new BookInDto();
        createBook.setName(name);
        createBook.setAuthor(author);
        createBook.setLanguage(lang);
        createBook.setDate(date);
        createBook.setDescription(desc);
        createBook.setGenre(genre);
        createBook.setPages(pages);
        createBook.setRating(rating);
        createBook.setBookIsNew(isNew);
        createBook.setPrice(price);
        createBook.setAmount(amount);
        return createBook;
    }

    @Test
    void toBookTest() {
        Book book = bookMapper.toBook(bookInDto);
        assertThat(book.getName()).isEqualTo(bookInDto.getName());
        assertThat(book.getAuthor()).isEqualTo(bookInDto.getAuthor());
        assertThat(book.getGenre()).isEqualTo(bookInDto.getGenre());
        assertThat(book.getBookIsNew()).isEqualTo(bookInDto.getBookIsNew());
        assertThat(book.getRating()).isEqualTo(bookInDto.getRating());
    }
}
