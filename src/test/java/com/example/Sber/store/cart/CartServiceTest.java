package com.example.Sber.store.cart;

import com.example.Sber.Store.cart.CartItem;
import com.example.Sber.Store.cart.CartRepository;
import com.example.Sber.Store.cart.CartServiceImpl;
import com.example.Sber.Store.cart.CartStorage;
import com.example.Sber.book.Book;
import com.example.Sber.user.User;
import com.example.Sber.user.UserRepository;
import com.example.Sber.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartServiceTest {

    @Mock
    private CartStorage cartStorage;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private CartServiceImpl cartService;

    private User user;
    private Book book;
    private CartItem cartItem;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);

        book = new Book();
        book.setId(1L);
        book.setName("Test Book");
        book.setPrice(100);

        cartItem = new CartItem();
        cartItem.setId(1L);
        cartItem.setUser(user);
        cartItem.setBook(book);
        cartItem.setQuantity(1L);

        when(userService.getCurrentUser()).thenReturn(user);
    }

    @Test
    void addItem_ShouldAddItemToCart() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(cartRepository.save(any(CartItem.class))).thenReturn(cartItem);

        CartItem result = cartService.addItem(book, 1L);

        assertNotNull(result);
        assertEquals(cartItem, result);
        verify(cartRepository, times(1)).save(any(CartItem.class));
    }

    @Test
    void removeItem_ShouldRemoveItemFromCart() {
        when(cartRepository.findByIdAndUserId(cartItem.getId(), user.getId())).thenReturn(cartItem);

        cartService.removeItem(cartItem.getId());

        verify(cartRepository, times(1)).delete(cartItem);
    }

    @Test
    void allCartItemsUser_ShouldReturnAllCartItemsForUser() {
        when(cartStorage.allItemsCartUser(user.getId())).thenReturn(Collections.singletonList(cartItem));

        Collection<CartItem> result = cartService.allCartItemsUser();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.contains(cartItem));
        verify(cartStorage, atLeastOnce()).allItemsCartUser(user.getId());
    }

    @Test
    void allCartItemsUser_ShouldReturnEmptyListIfNoItems() {
        when(cartStorage.allItemsCartUser(user.getId())).thenReturn(null);

        Collection<CartItem> result = cartService.allCartItemsUser();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(cartStorage, times(1)).allItemsCartUser(user.getId());
    }

    @Test
    void clearCart_ShouldClearCartForUser() {
        doNothing().when(cartRepository).deleteByUserId(user.getId());

        cartService.clearCart(user.getId());

        verify(cartRepository, times(1)).deleteByUserId(user.getId());
    }
}
