package com.example.fishingshop.DTOs.typeOfReel;

import com.example.fishingshop.models.Reel;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
public class TypeOfReelDTO{
    private Long id;
    private String type;
}
