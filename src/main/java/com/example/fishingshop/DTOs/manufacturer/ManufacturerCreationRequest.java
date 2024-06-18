package com.example.fishingshop.DTOs.manufacturer;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ManufacturerCreationRequest {
    @Pattern(regexp = "^[a-zA-Zа-яёА-ЯЁ0-9\\s\\-]+$",message = "Incorrect name of manufacturer")
    @Schema(defaultValue = "string")
    private String name;
}
