package com.example.demo.service;

import com.example.demo.model.Order;

import java.util.List;

public interface IOrderService {
    List<Order> getAllOrder();

    List<Order> getAllOrderByAccount(String account_id);
}
