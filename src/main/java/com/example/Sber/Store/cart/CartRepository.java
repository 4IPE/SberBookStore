package com.example.Sber.Store.cart;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<CartItem, Long> {
    CartItem findByIdAndUserId(Long cartId, Long userId);

    List<CartItem> findByUserId(Long userId);

    void deleteByUserId(Long userId);
}
