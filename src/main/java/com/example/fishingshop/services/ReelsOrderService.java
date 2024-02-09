package com.example.fishingshop.services;


import com.example.fishingshop.DTOs.orders.ReelsForOrderResponse;
import com.example.fishingshop.models.Order;

import java.util.List;

public interface ReelsOrderService {

    List<ReelsForOrderResponse> listByOrderId(Long id);



    void deleteById(Long id);

    void add( Order order);

}
