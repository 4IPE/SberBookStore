package com.example.Sber.book;

import com.example.Sber.Author.Author;
import com.example.Sber.book.enumarated.Genre;
import com.example.Sber.book.enumarated.Language;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @ManyToOne
    @JoinColumn(name = "author_id",nullable = false)
    private Author author;
    @Column(name = "lang")
    @Enumerated(value = EnumType.STRING)
    private Language language;
    @Column(name = "release_date",nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date date;
    @Column
    private String description;
    @Column
    @Enumerated(value = EnumType.STRING)
    private Genre genre;
    @Column
    private Integer pages;
    @Column
    private Integer rating;
    @Column(name = "book_is_new")
    private Boolean bookIsNew;
//    @Column
//    private String image;
    @Column
    private Integer price;
    @Column
    private Long amount;


}
