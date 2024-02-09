package com.example.fishingshop.DTOs.rod;

import lombok.Data;

@Data
public class RodEditRequest {
    private Long id;

    private String name;

    private Double price;

    private Long manufacturerId;
}
