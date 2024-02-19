package com.example.fishingshop.DTOs.rodsCart;

import com.example.fishingshop.DTOs.rod.RodDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RodCartResponse {
    private Long id;
    private RodDTO rod;
    private Integer amount;
}
