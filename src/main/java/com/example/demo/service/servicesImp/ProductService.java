package com.example.demo.service.servicesImp;

import com.example.demo.model.Product;
import com.example.demo.repository.IProductRepository;
import com.example.demo.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductService implements IProductService {
    @Autowired
    IProductRepository productRepository;

    @Override
    public long countProduces() {
        return productRepository.count();
    }

    @Override
    public List<Product> getAllProducts(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Product> homePage = productRepository.findAll(pageable);
        List<Product> products = homePage.getContent();
        return products;
    }

    @Override
    public List<Product> getProductByCategory(Integer categotyId) {
        return productRepository.getProductByCategory(categotyId);
    }
    @Override
    public Product getProductById(Integer id){
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteProduct(Integer id){
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> getAllProductsByFilter(Integer pageNo, Integer pageSize, String search, Integer categoryId) {
        List<Product> products;
        if(categoryId == -1){
            Pageable pageable = PageRequest.of(pageNo, pageSize);
            Page<Product> homePage = productRepository.findAll(pageable);
            products = homePage.getContent();
        } else  {
            products = productRepository.searchProductAndFilterByCategory(search,categoryId,PageRequest.of(pageNo, pageSize)).getContent();
        }
        return products;
    }
}
