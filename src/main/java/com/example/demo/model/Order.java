package com.example.demo.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "account_id")
    private String accountId;

    @Column(name = "order_date")
    private String OrderDate;

    @Column(name = "Address")
    private String Address;

    @Column(name = "total_price")
    private float price;

    @Column(name = "Status")
    private int status;
}
