package com.example.fishingshop.DTOs.reelsCart;

import lombok.Data;

@Data
public class ReelsCartCreationRequest {
    private Long userId;

    private Long reelId;

    private Integer amount;
}
