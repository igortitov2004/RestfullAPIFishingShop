package com.example.fishingshop.DTOs.orders;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    List<ReelsForOrderResponse> reelsForOrderResponseList;
    List<RodsForOrderResponse> rodsForOrderResponseList;
    Double totalPrice;
}
