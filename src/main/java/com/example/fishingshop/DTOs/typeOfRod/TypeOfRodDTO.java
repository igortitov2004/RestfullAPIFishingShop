package com.example.fishingshop.DTOs.typeOfRod;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TypeOfRodDTO {
    private Long id;
    @Pattern(regexp = "^[a-zA-Zа-яёА-ЯЁ0-9\\s\\-]+$",message = "Incorrect type")
    @Schema(defaultValue = "string")
    private String type;
}
