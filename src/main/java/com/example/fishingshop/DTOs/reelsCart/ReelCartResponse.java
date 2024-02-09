package com.example.fishingshop.DTOs.reelsCart;

import com.example.fishingshop.DTOs.UserDTO;
import com.example.fishingshop.DTOs.reel.ReelDTO;
import lombok.Data;

@Data
public class ReelCartResponse {
    private Long id;
    private ReelDTO reel;
    private Integer amount;
}
