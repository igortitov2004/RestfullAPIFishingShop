package com.example.fishingshop.DTOs.rodsCart;

import com.example.fishingshop.DTOs.UserDTO;
import com.example.fishingshop.DTOs.manufacturer.ManufacturerDTO;
import com.example.fishingshop.DTOs.rod.RodDTO;
import com.example.fishingshop.DTOs.typeOfRod.TypeOfRodDTO;
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
