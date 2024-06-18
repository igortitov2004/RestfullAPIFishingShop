package com.example.fishingshop.DTOs.reel;

import com.example.fishingshop.DTOs.manufacturer.ManufacturerDTO;
import com.example.fishingshop.DTOs.typeOfReel.TypeOfReelDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReelsResponse {
    private Long id;
    private String name;
    private Double price;
    private TypeOfReelDTO type;
    private ManufacturerDTO manufacturer;
    private String link;
}
