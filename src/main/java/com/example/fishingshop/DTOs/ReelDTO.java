package com.example.fishingshop.DTOs;

import com.example.fishingshop.models.Manufacturer;
import com.example.fishingshop.models.TypeOfReel;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class ReelDTO {

    private Long id;

    private String name;

    private Double price;

    private TypeOfReelDTO type;

    private ManufacturerDTO manufacturer;

}
