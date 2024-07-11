package com.example.Sber.Store.order;

import com.example.Sber.Store.order.model.Order;
import com.example.Sber.sec.CustomUserDetails;

import java.util.List;

public interface OrderService {
    Order addOrder(CustomUserDetails userDetails);

    List<Order> getAllOrderWithIdUser(CustomUserDetails userDetails);
}
