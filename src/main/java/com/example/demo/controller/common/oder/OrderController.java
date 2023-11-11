package com.example.demo.controller.common.oder;

import com.example.demo.controller.common.product.CartController;
import com.example.demo.model.Account;
import com.example.demo.model.Order;
import com.example.demo.model.Product;
import com.example.demo.service.servicesImp.OrderService;
import com.example.demo.service.servicesImp.ProductService;
import jakarta.servlet.http.HttpSession;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    ProductService productService;
    @GetMapping("/order")
    public String orderHistory(Model model, @RequestParam String accountId){
        List<Order> orderList = orderService.getAllOrderByAccount(accountId);
        model.addAttribute("orderList", orderList);
        return "common/orderHistory";
    }

    @GetMapping("/checkout")
    public String checkoutPage(Model model, HttpSession session, @CookieValue(value = "cart", defaultValue = "") String cart){
        Account account = (Account) session.getAttribute("account");
        List<Product> cartProduct = cookieToProductList(cart);
        int totalPrice = cookieToTotalPrice(cart);

        model.addAttribute("products", cartProduct);
        model.addAttribute("account", account);
        model.addAttribute("total",totalPrice);
        return "common/checkout";
    }

    @PostMapping("/processOrder")
    public String processOrder(HttpSession session) {
        return "redirect:/success";
    }

    public List<Product> cookieToProductList(String cookieValue) {
        ArrayList<Product> productList = new ArrayList<>();
        String[] productEntries = cookieValue.split("/");

        if (cookieValue.isEmpty()) {
            return productList;
        }

        for (String entry : productEntries) {
            String[] parts = entry.split(":");
            int productId = Integer.parseInt(parts[0]);
            int quantity = Integer.parseInt(parts[1]);

            Product product = productService.getProductById(productId);
            productList.add(product);

            for (int i = 0; i < quantity; i++) {
                if (product != null) {
                    product.setQuantity(quantity);
                    product.setTotalPrice(product.getPrice() * quantity);
                }
            }
        }
        return productList;
    }

    public int cookieToTotalPrice(String cookieValue) {
        int totalPrice = 0;

        // Check if the cookie value is empty
        if (cookieValue == null || cookieValue.isEmpty()) {
            return totalPrice;
        }

        String[] productEntries = cookieValue.split("/");

        for (String entry : productEntries) {
            String[] parts = entry.split(":");
            int productId = Integer.parseInt(parts[0]);
            int quantity = Integer.parseInt(parts[1]);

            Product product = productService.getProductById(productId);
            if (product != null) {
                totalPrice += product.getPrice() * quantity;
            }
        }

        return totalPrice;
    }

}
