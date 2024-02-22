package com.example.fishingshop.DTOs.manufacturer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ManufacturerCreationRequest {
    private String name;
}
