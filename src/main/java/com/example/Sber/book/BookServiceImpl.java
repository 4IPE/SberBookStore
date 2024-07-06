package com.example.Sber.book;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService{
    @Autowired
    private BookRepository bookRepository;
    public Book getBookWithId(Integer id) {
        return bookRepository.findById(id).orElse(new Book());
    }
}
