package com.example.fishingshop.services;


import com.example.fishingshop.DTOs.orders.RodsForOrderResponse;
import com.example.fishingshop.models.Order;

import java.util.List;

public interface RodsOrderService {

    List<RodsForOrderResponse> listByOrderId(Long id);

    Double add(Order order);
}
