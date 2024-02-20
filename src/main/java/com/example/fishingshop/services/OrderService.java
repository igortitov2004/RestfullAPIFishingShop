package com.example.fishingshop.services;

import com.example.fishingshop.DTOs.orders.OrderDTO;
import com.example.fishingshop.DTOs.orders.OrderResponse;
import com.example.fishingshop.models.Order;
import com.example.fishingshop.models.User;

import java.util.List;

public interface OrderService {
    Order add(String address, User user);
    List<OrderResponse> list(Long userId);
    void deleteById(Long id);
}
