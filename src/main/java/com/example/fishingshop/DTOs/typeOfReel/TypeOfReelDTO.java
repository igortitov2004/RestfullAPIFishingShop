package com.example.fishingshop.DTOs.typeOfReel;

import com.example.fishingshop.models.Reel;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
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
    @Pattern(regexp = "^[a-zA-Z0-9\\s\\-]+$",message = "Incorrect type")
    private String type;
}
