package com.example.fishingshop.services;

import com.example.fishingshop.DTOs.orders.ReelsForOrderRequestDTO;
import com.example.fishingshop.DTOs.orders.ReelsOrderDTO;
import com.example.fishingshop.models.Order;

import java.util.List;

public interface ReelsOrderService {
    List<ReelsOrderDTO> listByUserId(Long id);

    void deleteByUserId(Long id);

    void deleteById(Long id);

    void add(List<ReelsForOrderRequestDTO> dtosList, Order order);

}
