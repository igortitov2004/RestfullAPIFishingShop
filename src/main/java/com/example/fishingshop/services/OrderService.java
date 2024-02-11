package com.example.fishingshop.services;

import com.example.fishingshop.DTOs.orders.OrderDTO;
import com.example.fishingshop.DTOs.orders.OrderResponse;
import com.example.fishingshop.models.Order;

import java.util.List;

public interface OrderService {
    Order add(String address);
    List<OrderResponse> list(Long userId);
    void deleteById(Long id);
}
