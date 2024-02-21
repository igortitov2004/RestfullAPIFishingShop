package com.example.fishingshop.DTOs.typeOfReel;

import com.example.fishingshop.models.Reel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TypeOfReelDTO{
    private Long id;
    private String type;
}
