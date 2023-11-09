package com.example.demo.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "Name")
    private String name;

    @Column(name = "Price")
    private int price;

    @Column(name = "Description")
    private String description;

    @Column(name = "Image")
    private String image;

    @Column(name = "CategoryID")
    private int category;

    @Column(name = "Status")
    private int status;

    private int quantity;

    private int totalPrice;
}
