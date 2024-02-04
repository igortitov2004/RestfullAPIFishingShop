package com.example.fishingshop.DTOs;

import com.example.fishingshop.models.Manufacturer;
import com.example.fishingshop.models.TypeOfRod;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class RodDTO {


    private Long id;

    private String name;

    private Integer length;

    private Integer weight;

    private Integer testLoad;

    private TypeOfRodDTO type;

    private ManufacturerDTO manufacturer;
}
