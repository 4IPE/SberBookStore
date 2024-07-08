package com.example.Sber.Store.cart;

import java.util.Collection;

public interface CartStorage {
    Collection<CartItem> allItemsCartUser(Long id);
}
