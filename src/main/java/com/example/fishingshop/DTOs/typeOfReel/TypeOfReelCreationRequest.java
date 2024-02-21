package com.example.fishingshop.DTOs.typeOfReel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TypeOfReelCreationRequest {
    private String type;
}
