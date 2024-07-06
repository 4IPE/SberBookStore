package com.example.Sber.user;

import com.example.Sber.book.Book;

import java.util.List;

public interface UserService {
    Book addBook(Book book);
    User addUser(User user);
    List<User> getAllUsers ();
    void delUser (Long id);
    void userUpdRole (Long userid,Long roleId);
    User getCurrentUser();
}
