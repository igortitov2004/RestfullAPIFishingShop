package com.example.fishingshop.DTOs.rod;

import com.example.fishingshop.DTOs.manufacturer.ManufacturerDTO;
import com.example.fishingshop.DTOs.typeOfRod.TypeOfRodDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RodsResponse {
    private Long id;
    private String name;
    private Integer length;
    private Integer weight;
    private Integer testLoad;
    private Double price;
    private TypeOfRodDTO type;
    private ManufacturerDTO manufacturer;
    private String link;
}
