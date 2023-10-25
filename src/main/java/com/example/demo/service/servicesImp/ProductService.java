package com.example.demo.service.servicesImp;

import com.example.demo.model.Product;
import com.example.demo.repository.IProductRepository;
import com.example.demo.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductService implements IProductService {
    @Autowired
    IProductRepository productRepository;

    @Override
    public List<Product> getAllProducts(Integer pageNo, Integer pageSize, String search, Integer categoryId) {
        return productRepository.searchProductAndFilterByCategory(search,categoryId,PageRequest.of(pageNo, pageSize)).getContent();
    }
}
