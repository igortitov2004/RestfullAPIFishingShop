package com.example.fishingshop.DTOs;

import com.example.fishingshop.models.Rod;
import com.example.fishingshop.models.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class RodsCartDTO {
    private RodDTO rod;
    private Integer amount;
    private UserDTO user;
}
