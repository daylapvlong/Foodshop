package com.example.demo.controller.admin;

import com.example.demo.model.Account;
import com.example.demo.model.Order;
import com.example.demo.service.servicesImp.AccountService;
import com.example.demo.service.servicesImp.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class DashboardController {
    @Autowired
    AccountService accountService;
    @Autowired
    OrderService orderService;

    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model){
        List<Account> databaseAccounts = accountService.getAllAccount();
        List<Account> listAccounts = new ArrayList<>();
        List<Order> listOrders = orderService.getAllOrder();

        for (Order order : listOrders) {
            Account account = null;
            for (Account acc : databaseAccounts) {
                if (Objects.equals(acc.getId(), order.getId())) {
                    account = acc;
                    break;
                }
            }
            if (account != null) {
                listAccounts.add(account);
            }
        }
        double totalRevenue = 0;
        for (Order order : listOrders) {
            totalRevenue += order.getPrice();
        }
        model.addAttribute("totalRevenue", totalRevenue);

        model.addAttribute("listAccounts", listAccounts);
        model.addAttribute("listOrders", listOrders);
        return "admin/dashboard";
    }
}
