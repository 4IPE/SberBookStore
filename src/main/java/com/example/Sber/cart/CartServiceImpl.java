
package com.example.Sber.cart;

import com.example.Sber.book.Book;
import com.example.Sber.sec.CustomUserDetails;
import com.example.Sber.user.User;
import com.example.Sber.user.UserRepository;
import lombok.RequiredArgsConstructor;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @Override
    public CartItem addItem(Book book, Long quantity) {
        CartItem cartItem = new CartItem();
        cartItem.setUser(userRepository.findById(getCurrentUserId()).orElse(new User()));
        cartItem.setBook(book);
        cartItem.setQuantity(quantity);
       return cartRepository.save(cartItem);
    }
    @Override
    public void removeItem(Long cartId) {
        cartRepository.delete(cartRepository.findByIdAndUserId(cartId,getCurrentUserId()));
    }
    @Override
    public Collection<CartItem> allCartItemsUser(){
        System.out.println(getCurrentUserId());
        return cartStorage.allItemsCartUser(getCurrentUserId())!=null?cartStorage.allItemsCartUser(getCurrentUserId()):new ArrayList<>();
    }
    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof CustomUserDetails) {
                CustomUserDetails userDetails = (CustomUserDetails) principal;
                return userDetails.getUser().getId();
            } else {
                throw new IllegalArgumentException("Principal is not an instance of CustomUserDetails");
            }
        }
        throw new IllegalArgumentException("User not found in Security Context");
    }



}

