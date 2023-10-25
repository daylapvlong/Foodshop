package com.example.demo.repository;

import com.example.demo.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.net.ContentHandler;

@Repository
public interface IProductRepository extends JpaRepository<Product, Integer> {
    @Query(value = "SELECT * FROM product p " +
            "WHERE (LOWER(p.Name) LIKE LOWER(CONCAT('%', :search, '%'))" +
            "OR LOWER(p.Description) LIKE LOWER(CONCAT('%', :search, '%'))) " +
            "AND p.categoryId = :categoryId"
            ,nativeQuery = true)
    Page<Product> searchProductAndFilterByCategory(String search, Integer categoryId, Pageable pageable);
}
