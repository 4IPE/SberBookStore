package com.example.Sber.book;

import com.example.Sber.Author.Author;
import com.example.Sber.book.enumarated.Genre;
import com.example.Sber.book.enumarated.Language;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
public class BookInDto {
    private String name;
    private Author author;
    private Language language;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date date;
    private String description;
    private Genre genre;
    private Integer pages;
    private Integer rating;
    private Boolean bookIsNew;
    private Integer price;
    private Long amount;
}
