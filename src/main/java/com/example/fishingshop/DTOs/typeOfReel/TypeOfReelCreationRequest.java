package com.example.fishingshop.DTOs.typeOfReel;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TypeOfReelCreationRequest {
    @Pattern(regexp = "^[a-zA-Zа-яёА-ЯЁ0-9\\s\\-]+$",message = "Incorrect type")
    @Schema(defaultValue = "string")
    private String type;
}
