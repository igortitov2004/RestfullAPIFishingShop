package com.example.fishingshop.DTOs.reel;

import com.example.fishingshop.DTOs.manufacturer.ManufacturerDTO;
import com.example.fishingshop.DTOs.typeOfReel.TypeOfReelDTO;
import lombok.Data;

@Data
public class ReelDTO {

    private Long id;

    private String name;
    private Double price;
    private TypeOfReelDTO type;
    private ManufacturerDTO manufacturer;
}
