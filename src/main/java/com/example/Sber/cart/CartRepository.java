package com.example.Sber.cart;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<CartItem,Long> {
    CartItem findByIdAndUserId(Long cartId,Long userId);
}
