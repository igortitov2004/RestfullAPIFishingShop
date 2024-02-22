package com.example.fishingshop.DTOs.reel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReelCreationRequest {

    private String name;

    private Double price;

    private Long typeId;

    private Long manufacturerId;
}
