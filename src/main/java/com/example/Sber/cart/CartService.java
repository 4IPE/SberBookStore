package com.example.Sber.cart;

import com.example.Sber.book.Book;

import java.util.Collection;

public interface CartService {
    CartItem addItem(Book book, Long quantity);

    void removeItem(Long cartId);

    Collection<CartItem> allCartItemsUser();
}
