package com.example.demo.controller.admin;

import com.example.demo.model.Product;
import com.example.demo.service.servicesImp.CategoryService;
import com.example.demo.service.servicesImp.ProductService;
import com.example.demo.utils.Validate;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ResourceLoader resourceLoader;
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;

    @GetMapping("/admin/products")
    public String productPage(Model model,
                              @RequestParam(name = "page", defaultValue = "0") int pageNo,
                              @RequestParam(defaultValue = "15") int pageSize,
                              @RequestParam(defaultValue = "-1") int category,
                              @RequestParam(defaultValue = "") String search) {
        List<Product> productList = productService.getAllProductsByFilter(pageNo, pageSize, search, category);

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
        Product productUpdate = productService.getProductById(id);
        model.addAttribute("categoryList", categoryService.getAllCategory());
        model.addAttribute("productUpdate", productUpdate);
        return "/admin/productUpdate";
    }

    @PostMapping("/admin/productUpdate")
    public String updateProduct(Model model,
                                @RequestParam MultipartFile image,
                                @RequestParam int id,
                                @RequestParam String name,
                                @RequestParam String price,
                                @RequestParam String description,
                                @RequestParam int status,
                                @RequestParam int cateId) {
        int priceInput = Integer.parseInt(price);
        Product product = productService.getProductById(id);
        model.addAttribute("productUpdate", product);
        String avatarUrl = saveFile(image);
        product.setImage(avatarUrl);
        product.setName(name);
        product.setPrice(priceInput);
        product.setDescription(description);
        product.setStatus(status);
        product.setCategory(cateId);

        String errorMessage = checkValidateUpdateProduct(name, description);
        if (errorMessage != null) {
            model.addAttribute("errmsg", errorMessage);
            model.addAttribute("id", id);
            model.addAttribute("image", image);
            model.addAttribute("name", name);
            model.addAttribute("price", price);
            model.addAttribute("description", description);
            model.addAttribute("status", status);
            return "redirect:admin/updateProduct?id=" + id;
        } else {
            productService.saveProduct(product);
        }
        return "redirect:/admin/productDetail?id=" + id;
    }

    @GetMapping("/admin/addProduct")
    public String addProduct(Model model) {
        model.addAttribute("categoryList", categoryService.getAllCategory());
        return "/admin/productAdd";
    }

    @PostMapping("/admin/productAdd")
    public String addProductPost(Model model,
                                 @RequestParam MultipartFile image,
                                 @RequestParam String name,
                                 @RequestParam String price,
                                 @RequestParam String description,
                                 @RequestParam int cateId) {
        int priceInput = Integer.parseInt(price);
        String imgUrl = saveFile(image);
        model.addAttribute("name", name);
        model.addAttribute("price", price);
        model.addAttribute("description", description);
        model.addAttribute("image", image);
        model.addAttribute("cateId", cateId);
        model.addAttribute("category", categoryService.getAllCategory());

        String errorMessage = checkValidateUpdateProduct(name, description);
        if (errorMessage != null) {
            model.addAttribute("errmsg", errorMessage);
            model.addAttribute("image", image);
            model.addAttribute("name", name);
            model.addAttribute("price", price);
            model.addAttribute("cateId", cateId);
            model.addAttribute("description", description);
            model.addAttribute("category", categoryService.getAllCategory());
            return "/admin/productAdd";
        }
        Product product = new Product();
        product.setImage(imgUrl);
        product.setName(name);
        product.setPrice(priceInput);
        product.setDescription(description);
        product.setCategory(cateId);
        product.setStatus(1);
        productService.saveProduct(product);
        int newItemId = product.getId();

        return "redirect:admin/updateProduct?id=" + newItemId;
    }

    private String saveFile(MultipartFile file) {
        try {
            String uploadFolder = resourceLoader.getResource("classpath:").getFile().getAbsolutePath();
            uploadFolder += "/static/upload/";

            // Create folder if not exist
            File folder = new File(uploadFolder);
            if (!folder.exists()) folder.mkdirs();

            // Generate unique file name
            String fileName = file.getOriginalFilename();
            byte[] bytes = file.getBytes();

            Path uploadPath = Paths.get(uploadFolder + fileName);

            Files.write(uploadPath, bytes);

            return "/upload/" + fileName;
        } catch (Exception e) {
            System.out.println(e);
            return "/images/user_icon.png";
        }
    }

    private String checkValidateUpdateProduct(String name, String description) {
        name = name.trim();
        description = description.trim();

        if (name.isEmpty()) return "Please input product name";
        if (description.isEmpty()) return "Please input product description";
        if (!Validate.validTilteDescription(name, description)) return "Please dont input special characters";


        return null;
    }
}
