package com.example.Sber.cart;

import com.example.Sber.book.Book;

import java.util.Collection;

public interface CartStorage {
    Collection<CartItem> allItemsCartUser(Long id);
}
