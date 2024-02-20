package com.example.fishingshop.DTOs.carts;

import com.example.fishingshop.DTOs.carts.reelsCart.ReelForCartResponse;
import com.example.fishingshop.DTOs.carts.rodsCart.RodForCartResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartResponse {
    private List<ReelForCartResponse> reelForCartResponseList;
    private List<RodForCartResponse> rodForCartResponseList;
    private Double totalPrice;
}
