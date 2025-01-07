package com.example.fishingshop.DTOs.orders;

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
public class OrderRequest {
    @Pattern(regexp = "^[Ёёa-zA-ZA-Яа-я0-9\\s\\-,.]+$",message = "Incorrect address")
    @Schema(defaultValue = "string")
    private String address;
}
