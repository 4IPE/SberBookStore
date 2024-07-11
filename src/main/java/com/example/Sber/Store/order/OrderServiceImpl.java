package com.example.Sber.Store.order;


import com.example.Sber.Store.cart.CartItem;
import com.example.Sber.Store.cart.CartRepository;
import com.example.Sber.Store.cart.CartService;
import com.example.Sber.Store.order.model.Order;
import com.example.Sber.Store.order.model.OrderItem;
import com.example.Sber.Store.order.repository.OrderItemRepository;
import com.example.Sber.Store.order.repository.OrderRepository;
import com.example.Sber.book.Book;
import com.example.Sber.book.BookRepository;
import com.example.Sber.sec.CustomUserDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private CartService cartService;
    @Autowired
    private BookRepository bookRepository;

    @Override
    @Transactional
    public Order addOrder(CustomUserDetails userDetails) {
        List<CartItem> items = cartRepository.findByUserId(userDetails.getIdUser());

        Order order = new Order();
        order.setUser(userDetails.getUser());
        order.setOrderDate(LocalDate.now());
        Order orderFinal = orderRepository.save(order);

        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem item : items) {
            OrderItem orderItem = orderMapper.toOrderItem(item);
            orderItem.setOrder(order);
            orderItems.add(orderItem);
        }

        order.setItems(orderItems);

        order = orderRepository.save(order);
        orderItemRepository.saveAll(orderItems);
        cartService.clearCart(userDetails.getIdUser());
        items.forEach(item -> {
            Book book = item.getBook();
            book.setAmount(book.getAmount() - item.getQuantity());
            bookRepository.save(book);
        });
        return order;
    }

    @Override
    public List<Order> getAllOrderWithIdUser(CustomUserDetails userDetails) {
        List<Order> orders = orderRepository.findByUserId(userDetails.getIdUser());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        orders.forEach(order -> order.setFormattedOrderDate(order.getOrderDate().format(formatter)));

        return orders;
    }
}
