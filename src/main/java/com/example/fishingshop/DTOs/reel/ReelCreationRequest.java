package com.example.fishingshop.DTOs.reel;

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
public class ReelCreationRequest {
    @Pattern(regexp = "^[a-zA-Z0-9\\s\\-]+$",message = "Incorrect name")
    @Schema(defaultValue = "string")
    private String name;

    @Max(value = 10000,message = "Max price - 10000 BYN")
    @Min(value = 0,message = "Min price - 0 BYN")
    private Double price;

    private Long typeId;

    private Long manufacturerId;
    @Schema(defaultValue = "string")
    private String link;
}
