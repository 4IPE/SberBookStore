package com.example.Sber.Store.cart;

import com.example.Sber.book.BookRepository;
import com.example.Sber.exception.NotFound;
import com.example.Sber.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

@Repository
@Component
@Qualifier("bookShop")
@RequiredArgsConstructor
public class CartStorageImpl implements CartStorage {
    @Autowired
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    private final BookRepository bookRepository;
    @Autowired
    private final UserRepository userRepository;

    @Override
    public Collection<CartItem> allItemsCartUser(Long idUser) {
        return jdbcTemplate.query("SELECT * FROM carts c WHERE c.user_id = ?", new RowMapper<CartItem>() {
            @Override
            public CartItem mapRow(ResultSet rs, int rowNum) throws SQLException {
                CartItem cartItem = new CartItem();
                cartItem.setBook(bookRepository.findById(rs.getLong("book_id")).orElseThrow(NotFound::new));
                cartItem.setQuantity(rs.getLong("quantity"));
                cartItem.setUser(userRepository.findById(rs.getLong("user_id")).orElseThrow(NotFound::new));
                cartItem.setId(rs.getLong("id"));
                return cartItem;
            }
        }, idUser);
    }

}
