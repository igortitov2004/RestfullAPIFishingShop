package com.example.fishingshop.DTOs.rodsCart;

import com.example.fishingshop.DTOs.UserDTO;
import com.example.fishingshop.DTOs.manufacturer.ManufacturerDTO;
import com.example.fishingshop.DTOs.rod.RodDTO;
import com.example.fishingshop.DTOs.typeOfRod.TypeOfRodDTO;
import lombok.Data;

@Data
public class RodCartResponse {
    private Long id;
    private RodDTO rod;
    private Integer amount;
}
