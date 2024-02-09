package com.example.fishingshop.DTOs.manufacturer;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class ManufacturerDTO {
    private Long id;
    private String name;
}
