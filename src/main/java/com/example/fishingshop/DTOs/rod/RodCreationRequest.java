package com.example.fishingshop.DTOs.rod;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RodCreationRequest {
    private String name;
    private Integer length;
    private Integer weight;
    private Integer testLoad;
    private Double price;
    private Long typeId;
    private Long manufacturerId;
}
