package com.example.fishingshop.DTOs.reelsCart;

import com.example.fishingshop.DTOs.UserDTO;
import com.example.fishingshop.DTOs.reel.ReelDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReelCartResponse {
    private Long id;
    private ReelDTO reel;
    private Integer amount;
}
