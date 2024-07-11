package com.example.Sber.store.order;

import com.example.Sber.Store.order.OrderController;
import com.example.Sber.Store.order.OrderService;
import com.example.Sber.sec.TestSecurityConfig;
import com.example.Sber.user.Role;
import com.example.Sber.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = OrderController.class)
@Import(TestSecurityConfig.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Test
    void getUserOrdersTest() throws Exception {
        Role role = new Role();
        role.setName("USER");
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("password");
        user.setRole(role);


        when(orderService.getAllOrderWithIdUser(any())).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/order"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("order"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("orders"));
    }

    @Test
    void addOrderTest() throws Exception {
        Role role = new Role();
        role.setName("USER");
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("password");
        user.setRole(role);


        mockMvc.perform(MockMvcRequestBuilders.post("/order/add-order"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/order"));

    }
}
