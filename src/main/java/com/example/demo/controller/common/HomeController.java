package com.example.demo.controller.common;

import com.example.demo.model.Category;
import com.example.demo.model.Product;
import com.example.demo.service.servicesImp.CategoryService;
import com.example.demo.service.servicesImp.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;


@Controller
public class HomeController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;
    @GetMapping("/home")
    public String homePage(Model model,
                           @RequestParam(defaultValue = "0") int pageNo,
                           @RequestParam(defaultValue = "10") int pageSize,
                           @RequestParam(defaultValue = "") String search,
                           @RequestParam(defaultValue = "-1") int category){
        model.addAttribute("listCategory", categoryService.getAllCategory());

        List<Product> productList = productService.getAllProducts(pageNo,pageSize,search,category);
        model.addAttribute("data", productList);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("search", search);
        model.addAttribute("category", category);
        return "/common/home";
    }

    @PostMapping("/home")
    public String filterPage(Model model){
        return "/common/home";
    }


}
