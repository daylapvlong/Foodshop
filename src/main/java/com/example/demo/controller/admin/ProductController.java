package com.example.demo.controller.admin;

import com.example.demo.model.Product;
import com.example.demo.service.servicesImp.CategoryService;
import com.example.demo.service.servicesImp.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;
    @GetMapping("/admin/products")
    public String productPage(Model model,
                              @RequestParam(name = "page", defaultValue = "0") int pageNo,
                              @RequestParam(defaultValue = "15") int pageSize,
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
        return "/admin/productList";
    }

    @GetMapping("/admin/productDetail")
    public String productDetail(Model model, @RequestParam Integer id) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "/admin/productDetail";
    }

    @GetMapping("/admin/deleteProduct")
    public String deleteProduct(@RequestParam int id) {
        productService.deleteProduct(id);
        return "redirect:/admin/productDetail?id=" + id;
    }

    @GetMapping("admin/updateProduct")
    public String updateProduct(Model model, @RequestParam Integer id) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "/admin/productUpdate";
    }

    @GetMapping("/admin/addProduct")
    public String addProduct() {
        return "/admin/productAdd";
    }
}
