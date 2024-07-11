package com.example.Sber.book;

import com.example.Sber.exception.NotFound;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book book;
    private BookInDto bookInDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        book = new Book();
        book.setId(1L);
        book.setName("Book");
        book.setRating(10);

        bookInDto = new BookInDto();
        bookInDto.setName("Book");
        bookInDto.setRating(10);
    }

    @Test
    void getBookWithId_ShouldReturnBook_WhenBookExists() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Book foundBook = bookService.getBookWithId(1L);

        assertNotNull(foundBook);
        assertEquals(book.getId(), foundBook.getId());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void getBookWithId_ShouldThrowNotFound_WhenBookDoesNotExist() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFound.class, () -> bookService.getBookWithId(1L));
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void addBook_ShouldSaveBook() {
        when(bookMapper.toBook(bookInDto)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);

        Book savedBook = bookService.addBook(bookInDto, "default.jpg");

        assertNotNull(savedBook);
        assertEquals("default.jpg", savedBook.getImage());
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void search_ShouldReturnBooks_WhenQueryMatches() {
        List<Book> books = Collections.singletonList(book);
        when(bookRepository.findByNameContainingIgnoreCase("Book")).thenReturn(books);

        List<Book> foundBooks = bookService.search("Book");

        assertNotNull(foundBooks);
        assertEquals(1, foundBooks.size());
        verify(bookRepository, times(1)).findByNameContainingIgnoreCase("Book");
    }

    @Test
    void deleteBook_ShouldDeleteBook() {
        bookService.deleteBook(1L);

        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    void allBooks_ShouldReturnAllBooksSortedByRating() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Order.desc("rating")));
        when(bookRepository.findAll(pageable)).thenReturn(Page.empty());

        List<Book> allBooks = bookService.allBooks();

        assertNotNull(allBooks);
        assertEquals(0, allBooks.size());
        verify(bookRepository, times(1)).findAll(pageable);
    }
}
