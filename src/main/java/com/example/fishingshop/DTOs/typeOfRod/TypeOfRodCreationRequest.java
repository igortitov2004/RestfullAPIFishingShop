package com.example.fishingshop.DTOs.typeOfRod;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TypeOfRodCreationRequest {
    @Pattern(regexp = "^[a-zA-Z0-9\\s\\-]+$",message = "Incorrect type")
    private String type;
}
