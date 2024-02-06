package com.example.fishingshop.DTOs.rodsCart;

import lombok.Data;

@Data
public class RodsCartCreationRequest {
    private Long rodId;
    private Integer amount;
    private Long userId;
}
