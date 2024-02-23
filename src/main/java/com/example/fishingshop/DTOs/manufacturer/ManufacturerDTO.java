package com.example.fishingshop.DTOs.manufacturer;

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
@AllArgsConstructor
@NoArgsConstructor
public class ManufacturerDTO {
    private Long id;
    @Pattern(regexp = "^[a-zA-Z0-9\\s\\-]+$",message = "Incorrect name of manufacturer")
    private String name;
}
