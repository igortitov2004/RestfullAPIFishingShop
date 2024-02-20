package com.example.fishingshop.DTOs.carts.rodsCart;

import com.example.fishingshop.DTOs.user.UserDTO;
import com.example.fishingshop.DTOs.rod.RodDTO;
import lombok.Data;

@Data
public class RodsCartDTO {
    private Long id;
    private RodDTO rod;
    private Integer amount;
    private UserDTO user;
}
