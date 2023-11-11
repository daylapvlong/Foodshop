package com.example.demo.controller.common;

import com.example.demo.model.Category;
import com.example.demo.model.Product;
import com.example.demo.service.servicesImp.CategoryService;
import com.example.demo.service.servicesImp.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
                           @RequestParam(name = "page", defaultValue = "0") int pageNo,
                           @RequestParam(defaultValue = "16") int pageSize,
                           @RequestParam(defaultValue = "-1") int category,
                           @RequestParam(defaultValue = "") String search){
        List<Product> productList = productService.getAllProductsByFilter(pageNo,pageSize,search,category);

        long maxPage = (productService.countProduces() + pageSize - 1) / pageSize;
        int startProduct = (pageNo - 1) * pageSize;

        int startPage = Math.max(0, pageNo - 1);
        long endPage = Math.min(maxPage, 5);

        model.addAttribute("listCategory", categoryService.getAllCategory());
        model.addAttribute("data", productList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("thisPage", pageNo);
        model.addAttribute("endPage", endPage);
        model.addAttribute("maxPage", maxPage);
        return "/common/home";
    }

    @PostMapping("/home")
    public String filterPage(Model model, @RequestParam(defaultValue = "0") int pageNo,
                             @RequestParam int pageSize,
                             @RequestParam String search,
                             @RequestParam int category){

        List<Product> productList = productService.getAllProductsByFilter(pageNo,pageSize,search,category);
        model.addAttribute("data", productList);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("search", search);
        model.addAttribute("category", category);
        return "/common/home";
    }
}
