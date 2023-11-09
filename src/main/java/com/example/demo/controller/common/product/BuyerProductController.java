package com.example.demo.controller.common.product;

import com.example.demo.model.Product;
import com.example.demo.service.servicesImp.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class BuyerProductController {
    @Autowired
    ProductService productService;
    @GetMapping("/productBuyerDetail")
    public String productBuyerDetail(Model model, @RequestParam Integer id, @RequestParam Integer categoryId) {
        Product product = productService.getProductById(id);
        List<Product> productByCategory = productService.getProductByCategory(categoryId);
        model.addAttribute("product", product);
        model.addAttribute("category", productByCategory);
        return "/common/productShopDetail";
    }
}
