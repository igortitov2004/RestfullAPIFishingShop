package com.example.fishingshop.DTOs.reelsCart;

import com.example.fishingshop.DTOs.reel.ReelDTO;
import com.example.fishingshop.DTOs.user.UserDTO;
import lombok.Data;

@Data

public class ReelsCartDTO {
    private Long id;
    private UserDTO user;
    private ReelDTO reel;
    private Integer amount;
}
