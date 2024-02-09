package com.example.fishingshop.DTOs.orders;

import lombok.Data;

import java.util.List;

@Data
public class OrderResponse {
    List<ReelsForOrderResponse> reelsForOrderResponseList;
    List<RodsForOrderResponse> rodsForOrderResponseList;
}
