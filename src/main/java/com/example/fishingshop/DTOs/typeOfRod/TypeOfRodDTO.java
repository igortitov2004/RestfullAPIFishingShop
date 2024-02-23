package com.example.fishingshop.DTOs.typeOfRod;

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
    @Pattern(regexp = "^[a-zA-Z0-9\\s\\-]+$",message = "Incorrect type")
    private String type;
}
