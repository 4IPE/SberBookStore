package com.example.Sber.Store.order.repository;

import com.example.Sber.Store.order.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
