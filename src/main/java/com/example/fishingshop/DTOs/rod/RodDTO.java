package com.example.fishingshop.DTOs.rod;

import com.example.fishingshop.DTOs.manufacturer.ManufacturerDTO;
import com.example.fishingshop.DTOs.typeOfRod.TypeOfRodDTO;
import lombok.Data;

@Data
public class RodDTO {
    private Long id;
    private String name;
    private Integer length;
    private Integer weight;
    private Integer testLoad;
    private Double price;
    private TypeOfRodDTO type;
    private ManufacturerDTO manufacturer;
}
