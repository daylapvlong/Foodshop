package com.example.demo.controller.common;

import com.example.demo.model.Product;
import com.example.demo.service.servicesImp.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class BaseController {
    @Autowired
    ProductService productService;

    @ModelAttribute
    public void addCommonAttributes(Model model, @CookieValue(value = "cart", defaultValue = "") String cart) {
        List<Product> cartProduct = cookieToProductList(cart);
        int countProduct = cartProduct.size();
        model.addAttribute("countProduct", countProduct);
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
}
