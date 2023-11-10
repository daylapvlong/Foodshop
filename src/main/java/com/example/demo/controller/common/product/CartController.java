package com.example.demo.controller.common.product;

import com.example.demo.model.Product;
import com.example.demo.service.servicesImp.ProductService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller
public class CartController {
    @Autowired
    ProductService productService;
    @PostMapping("/addtocart")
    public String processRequest(@RequestParam String productID, @CookieValue(value = "cart", defaultValue = "") String cart, HttpServletResponse response) {
        if (cart.isEmpty()) {
            cart = productID + ":1";
        } else {
            String[] products = cart.split("/");
            boolean isExist = false;
            for (int i = 0; i < products.length; i++) {
                String[] product = products[i].split(":");
                if (product[0].equals(productID)) {
                    int quantity = Integer.parseInt(product[1]) + 1;
                    products[i] = productID + ":" + quantity;
                    isExist = true;
                    break;
                }
            }
            cart = String.join("/", products);
            if (!isExist) {
                cart += "/" + productID + ":1";
            }
        }
        Cookie cookie = new Cookie("cart", cart);
        cookie.setMaxAge(60 * 60 * 24);
        response.addCookie(cookie);
        return "redirect:/home";
    }

    @GetMapping("/cart")
    public String cart(Model model, @CookieValue(value = "cart", defaultValue = "") String cart){
        List<Product> cartProduct = cookieToProductList(cart);
        int totalPrice = cookieToTotalPrice(cart);

        model.addAttribute("cartList", cartProduct);
        model.addAttribute("total", totalPrice);

        return "common/cart";
    }

    @PostMapping("/updatecart")
    public String updateCart(@CookieValue(value = "cart", defaultValue = "") String cart,
                             HttpServletResponse response, @RequestParam Map<String, String> params) {
        List<Product> cartProduct = cookieToProductList(cart);

        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (entry.getKey().startsWith("quantity-")) {
                int productId = Integer.parseInt(entry.getKey().substring("quantity-".length()));
                int quantity = Integer.parseInt(entry.getValue());

                // Update the quantity of the specified product
                for (Product product : cartProduct) {
                    if (product.getId().equals(productId)) {
                        product.setQuantity(quantity);
                        break;
                    }
                }
            }
        }

        String updatedCart = productToCookie(cartProduct);
        Cookie cookie = new Cookie("cart", updatedCart);
        cookie.setMaxAge(60 * 60 * 24);
        cookie.setPath("/");
        response.addCookie(cookie);
        return "redirect:/cart";
    }

    @GetMapping("/cart/delete/{productId}")
    public String deleteProduct(@PathVariable int productId, HttpServletResponse response,
                                @CookieValue(value = "cart", defaultValue = "") String cart) {
        // Get the current list of products from the cookie
        List<Product> cartProduct = cookieToProductList(cart);

        // Remove the product with the given productId
        cartProduct.removeIf(product -> product.getId().equals(productId));

        // Convert the updated list back to a string and update the cookie
        String updatedCart = productToCookie(cartProduct);
        Cookie cookie = new Cookie("cart", updatedCart);
        cookie.setMaxAge(60 * 60 * 24); // Set the cookie's max age (in seconds), adjust as needed
        cookie.setPath("/");
        response.addCookie(cookie);

        return "redirect:/cart";
    }

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

    private String productToCookie(List<Product> products) {
        StringBuilder cartString = new StringBuilder();

        for (Product product : products) {
            cartString.append(product.getId()).append(":").append(product.getQuantity()).append("/");
        }

        // Remove the trailing slash
        if (cartString.length() > 0) {
            cartString.setLength(cartString.length() - 1);
        }
        System.out.println(cartString);
        return cartString.toString();
    }


}
