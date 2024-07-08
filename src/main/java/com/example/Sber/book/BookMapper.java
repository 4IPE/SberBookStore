package com.example.Sber.book;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {
    @Mapping(target = "image", ignore = true)
    Book toBook(BookInDto book);
}
