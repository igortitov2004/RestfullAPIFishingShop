package com.example.fishingshop.DTOs.orders;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    Long id;
    List<ReelsForOrderResponse> reelsForOrderResponseList;
    List<RodsForOrderResponse> rodsForOrderResponseList;
    Double totalPrice;
    String localDateTime;
    String address;
}
