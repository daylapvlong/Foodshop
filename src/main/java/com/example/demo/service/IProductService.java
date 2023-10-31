package com.example.demo.service;

import com.example.demo.model.Product;

import java.util.List;

public interface IProductService {
    long countProduces();

    List<Product> getAllProducts(int pageNo, int pageSize);

    List<Product> getProductByCategory(Integer categotyId);

    Product getProductById(Integer id);

    void deleteProduct(Integer id);

    List<Product> getAllProductsByFilter(Integer pageNo, Integer pageSize, String search, Integer categoryId);
}
