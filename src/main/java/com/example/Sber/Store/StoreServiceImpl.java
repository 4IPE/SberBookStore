package com.example.Sber.Store;

import com.example.Sber.book.Book;
import com.example.Sber.book.BookRepository;
import com.example.Sber.exception.NotFound;
import com.example.Sber.sec.CustomUserDetails;
import com.example.Sber.user.User;
import com.example.Sber.user.UserRepository;
import com.example.Sber.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;


    public List<Book> getAllBook() {
        Pageable pageable = PageRequest.of(0, 10);
        return  bookRepository.findAll(pageable) != null ? bookRepository.findAll() : new ArrayList<>();
    }
    public User getCurrentUser() {
        return  userService.getCurrentUser();
    }
}
