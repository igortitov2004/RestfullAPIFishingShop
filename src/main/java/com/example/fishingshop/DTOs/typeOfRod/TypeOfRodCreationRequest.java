package com.example.fishingshop.DTOs.typeOfRod;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TypeOfRodCreationRequest {
    private String type;
}
