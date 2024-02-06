package com.example.fishingshop.DTOs.orders;

import com.example.fishingshop.DTOs.RodDTO;
import com.example.fishingshop.DTOs.UserDTO;
import com.example.fishingshop.models.Rod;
import com.example.fishingshop.models.User;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class RodsOrderDTO {
    private Long id;
    private RodDTO rod;
    private Integer amount;
}
