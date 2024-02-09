package com.example.fishingshop.DTOs.orders;

import com.example.fishingshop.DTOs.reel.ReelDTO;
import lombok.Data;

@Data
public class ReelsForOrderResponse {
    private ReelDTO reelDTO;
    private Integer amount;
}
