package com.example.Sber.Store;

import com.example.Sber.book.BookRepository;
import com.example.Sber.user.UserRepository;
import com.example.Sber.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;


}
