package com.example.demo.service;

import com.example.demo.model.Product;

import java.util.List;

public interface IProductService {
    List<Product> getAllProducts(Integer pageNo, Integer pageSize, String search, Integer categoryId);
}
