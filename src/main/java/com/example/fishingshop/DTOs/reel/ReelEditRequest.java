package com.example.fishingshop.DTOs.reel;


import lombok.Data;

@Data
public class ReelEditRequest{
    private Long id;

    private String name;

    private Double price;

    private Long manufacturerId;
}
