package com.example.fishingshop.DTOs.orders;

import com.example.fishingshop.DTOs.reel.ReelDTO;
import lombok.Data;

@Data
public class ReelsOrderDTO {

    private Long id;

    private ReelDTO reel;

    private Integer amount;


}