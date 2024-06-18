package com.example.fishingshop.DTOs.rod;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(defaultValue = "string")
    private String name;
    @Max(value = 6,message = "Max length - 6 m")
    @Min(value = 1,message = "Min length - 1 m")
    private Integer length;
    @Max(value = 1000,message = "Max weight - 1000 g")
    @Min(value = 150,message = "Min weight - 150 g")
    private Integer weight;
    @Max(value = 300,message = "Max test load - 300 g")
    @Min(value = 3,message = "Min test load - 3 g")
    private Integer testLoad;
    @Max(value = 10000,message = "Max price - 10000 BYN")
    @Min(value = 0,message = "Min price - 0 BYN")
    private Double price;
    private Long typeId;
    private Long manufacturerId;

    @Schema(defaultValue = "string")
    private String link;
}
