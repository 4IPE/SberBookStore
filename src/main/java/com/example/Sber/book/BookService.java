package com.example.Sber.book;

import java.util.List;

public interface BookService {
    Book getBookWithId(Long id);

    Book addBook(BookInDto book, String path);

    List<Book> search(String name);

    void deleteBook(Long bookId);

    List<Book> allBooks();
}
