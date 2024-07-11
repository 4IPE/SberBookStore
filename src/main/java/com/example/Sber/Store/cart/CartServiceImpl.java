package com.example.Sber.Store.cart;

import com.example.Sber.book.Book;
import com.example.Sber.user.User;
import com.example.Sber.user.UserRepository;
import com.example.Sber.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    @Autowired
    private CartStorage cartStorage;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Override
    public CartItem addItem(Book book, Long quantity) {
        CartItem cartItem = new CartItem();
        cartItem.setUser(userRepository.findById(userService.getCurrentUser().getId()).orElse(new User()));
        cartItem.setBook(book);
        cartItem.setQuantity(quantity);
        return cartRepository.save(cartItem);
    }

    @Override
    public void removeItem(Long cartId) {
        cartRepository.delete(cartRepository.findByIdAndUserId(cartId, userService.getCurrentUser().getId()));
    }

    @Override
    public Collection<CartItem> allCartItemsUser() {
        return cartStorage.allItemsCartUser(userService.getCurrentUser().getId()) != null ? cartStorage.allItemsCartUser(userService.getCurrentUser().getId()) : new ArrayList<>();
    }

    @Override
    public void clearCart(Long userId) {
        cartRepository.deleteByUserId(userId);
    }
}

