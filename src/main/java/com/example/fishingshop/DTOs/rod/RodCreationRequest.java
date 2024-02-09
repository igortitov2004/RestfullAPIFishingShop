package com.example.fishingshop.DTOs.rod;

import lombok.Data;

@Data
public class RodCreationRequest {
    private String name;
    private Integer length;
    private Integer weight;
    private Integer testLoad;
    private Double price;
    private Long typeId;
    private Long manufacturerId;
}
