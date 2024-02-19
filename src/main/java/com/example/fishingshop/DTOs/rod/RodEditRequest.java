package com.example.fishingshop.DTOs.rod;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RodEditRequest {
    private Long id;

    private String name;

    private Double price;

    private Long manufacturerId;
}
