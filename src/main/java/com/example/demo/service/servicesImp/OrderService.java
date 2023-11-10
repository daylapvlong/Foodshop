package com.example.demo.service.servicesImp;

import com.example.demo.model.Order;
import com.example.demo.repository.IOrderRepository;
import com.example.demo.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService implements IOrderService {
    @Autowired
    IOrderRepository orderRepository;
    @Override
    public List<Order> getAllOrder(){return orderRepository.findAll();}

    @Override
    public List<Order> getAllOrderByAccount(String account_id){
        return orderRepository.findOrderByAccountId(account_id);
    }
}
