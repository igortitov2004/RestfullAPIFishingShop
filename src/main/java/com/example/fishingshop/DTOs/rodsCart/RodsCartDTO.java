package com.example.fishingshop.DTOs.rodsCart;

import com.example.fishingshop.DTOs.UserDTO;
import com.example.fishingshop.DTOs.RodDTO;
import lombok.Data;

@Data
public class RodsCartDTO {

    private Long id;
    private RodDTO rod;
    private Integer amount;
    private UserDTO user;
}
