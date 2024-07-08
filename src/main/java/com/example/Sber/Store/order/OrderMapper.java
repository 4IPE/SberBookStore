package com.example.Sber.Store.order;

import com.example.Sber.Store.cart.CartItem;
import com.example.Sber.Store.order.model.OrderItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderItem toOrderItem(CartItem item);
}
