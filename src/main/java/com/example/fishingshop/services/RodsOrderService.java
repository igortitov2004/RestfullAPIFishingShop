package com.example.fishingshop.services;

import com.example.fishingshop.DTOs.orders.RodsForOrderRequestDTO;
import com.example.fishingshop.DTOs.orders.RodsOrderDTO;
import com.example.fishingshop.models.Order;

import java.util.List;

public interface RodsOrderService {
    List<RodsOrderDTO> listByUserId(Long id);
    void deleteByUserId(Long id);
    void deleteById(Long id);
    void add(List<RodsForOrderRequestDTO> dtosList, Order order);
}
