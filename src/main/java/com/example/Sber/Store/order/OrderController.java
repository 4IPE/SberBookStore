package com.example.Sber.Store.order;

import com.example.Sber.sec.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order")
@Slf4j
@RequiredArgsConstructor
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping
    public String getUserOrders(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        model.addAttribute("orders", orderService.getAllOrderWithIdUser(userDetails));
        return "order";
    }

    @PostMapping("/add-order")
    public String addOrder(@AuthenticationPrincipal CustomUserDetails userDetails) {
        orderService.addOrder(userDetails);
        return "redirect:/order";
    }

}
