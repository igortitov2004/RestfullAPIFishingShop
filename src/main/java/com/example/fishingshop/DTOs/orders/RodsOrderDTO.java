package com.example.fishingshop.DTOs.orders;

import com.example.fishingshop.DTOs.rod.RodDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RodsOrderDTO {
    private Long id;
    private RodDTO rod;
    private Integer amount;
}
