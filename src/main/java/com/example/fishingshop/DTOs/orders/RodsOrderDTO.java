package com.example.fishingshop.DTOs.orders;

import com.example.fishingshop.DTOs.rod.RodDTO;
import lombok.Data;

@Data
public class RodsOrderDTO {
    private Long id;
    private RodDTO rod;
    private Integer amount;
}