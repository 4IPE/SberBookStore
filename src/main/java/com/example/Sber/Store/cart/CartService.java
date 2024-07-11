package com.example.Sber.Store.cart;

import com.example.Sber.book.Book;

import java.util.Collection;

public interface CartService {
    CartItem addItem(Book book, Long quantity);

    void removeItem(Long cartId);

    Collection<CartItem> allCartItemsUser();

    void clearCart(Long userId);
}
