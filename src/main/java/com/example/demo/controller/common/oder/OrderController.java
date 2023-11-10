package com.example.demo.controller.common.oder;

import com.example.demo.model.Order;
import com.example.demo.service.servicesImp.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class OrderController {
    @Autowired
    OrderService orderService;
    @GetMapping("/order")
    public String orderHistory(Model model, @RequestParam String accountId){
        List<Order> orderList = orderService.getAllOrderByAccount(accountId);
        model.addAttribute("orderList", orderList);
        return "common/orderHistory";
    }
}
