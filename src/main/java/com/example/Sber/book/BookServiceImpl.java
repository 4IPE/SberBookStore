package com.example.Sber.book;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookMapper bookMapper;

    public Book getBookWithId(Long id) {
        return bookRepository.findById(id).orElse(new Book());
    }

    public Book addBook(BookInDto bookIn, String path) {
        Book book = bookMapper.toBook(bookIn);
        book.setImage(path);
        System.out.println(book);
        return bookRepository.save(book);
    }

    public List<Book> search(String query) {
        return bookRepository.findByNameContainingIgnoreCase(query);
    }

    @Override
    public void deleteBook(Long bookId) {
        bookRepository.deleteById(bookId);
    }

    public List<Book> allBooks() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Order.desc("rating")));
        return bookRepository.findAll(pageable) != null ? bookRepository.findAll() : new ArrayList<>();
    }
}
