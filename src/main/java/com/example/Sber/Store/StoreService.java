package com.example.Sber.Store;


import com.example.Sber.book.Book;
import com.example.Sber.user.User;

import java.util.List;

public interface StoreService {
    List<Book> getAllBook();
    User getCurrentUser();
}
