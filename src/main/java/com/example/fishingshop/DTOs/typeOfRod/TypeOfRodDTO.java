package com.example.fishingshop.DTOs.typeOfRod;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class TypeOfRodDTO {
    private Long id;
    private String type;
}