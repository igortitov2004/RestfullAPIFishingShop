package com.example.fishingshop.DTOs.reel;

import lombok.Data;

@Data
public class ReelCreationRequest {

    private String name;

    private Double price;

    private Long typeId;

    private Long manufacturerId;
}
