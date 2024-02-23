package com.example.fishingshop.DTOs.orders;

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
    @Pattern(regexp = "^[a-zA-Z0-9\\s\\-,.]+$",message = "Incorrect address")
    private String address;
}
