package com.example.Sber.store.order;

import com.example.Sber.Author.Author;
import com.example.Sber.Store.order.model.Order;
import com.example.Sber.Store.order.model.OrderItem;
import com.example.Sber.Store.order.repository.OrderRepository;
import com.example.Sber.book.Book;
import com.example.Sber.book.enumarated.Genre;
import com.example.Sber.book.enumarated.Language;
import com.example.Sber.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource(properties = {"db.name=test"})
public class OrderRepositoryTest {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TestEntityManager entityManager;

    private User user;
    private Order order1;
    private Order order2;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setUsername("testUser");
        user.setPassword("password");

        entityManager.persist(user);
        entityManager.flush();

        Author author = new Author();
        author.setName("author");

        entityManager.persist(author);
        entityManager.flush();

        Book createBook = new Book();
        createBook.setName("name");
        createBook.setAuthor(author);
        createBook.setLanguage(Language.EN);
        createBook.setDate(new Date(103, 01, 01));
        createBook.setDescription("desc");
        createBook.setGenre(Genre.COMEDIA);
        createBook.setPages(12);
        createBook.setRating(10);
        createBook.setBookIsNew(true);
        createBook.setImage("default.jpg");
        createBook.setPrice(12);
        createBook.setAmount(12L);

        entityManager.persist(createBook);
        entityManager.flush();

        order1 = new Order();
        order1.setOrderDate(LocalDate.now());
        order1.setUser(user);

        order2 = new Order();
        order2.setOrderDate(LocalDate.now().plusDays(1));
        order2.setUser(user);

        OrderItem item1 = new OrderItem();
        item1.setQuantity(2L);
        item1.setBook(createBook);
        item1.setOrder(order1);

        OrderItem item2 = new OrderItem();
        item2.setQuantity(1L);
        item2.setBook(createBook);
        item2.setOrder(order2);

        order1.setItems(List.of(item1));
        order2.setItems(List.of(item2));

        entityManager.persist(order1);
        entityManager.persist(order2);
        entityManager.flush();
    }

    @Test
    public void testFindByUserId() {
        List<Order> orders = orderRepository.findByUserId(user.getId());

        assertNotNull(orders);
        assertEquals(2, orders.size());
        assertTrue(orders.contains(order1));
        assertTrue(orders.contains(order2));
    }
}
