package com.example.fishingshop.DTOs.rod;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RodCreationRequest {
    @Pattern(regexp = "^[a-zA-Z0-9\\s\\-]+$",message = "Incorrect name")
    private String name;
    @Max(6)
    @Min(1)
    private Integer length;
    @Max(1000)
    @Min(150)
    private Integer weight;
    @Max(300)
    @Min(3)
    private Integer testLoad;
    @Max(10000)
    @Min(25)
    private Double price;
    private Long typeId;
    private Long manufacturerId;
}
