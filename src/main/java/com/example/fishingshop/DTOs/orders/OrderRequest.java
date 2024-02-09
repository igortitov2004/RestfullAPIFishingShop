package com.example.fishingshop.DTOs.orders;

import com.example.fishingshop.DTOs.reelsCart.ReelsCartDTO;
import com.example.fishingshop.DTOs.rodsCart.RodsCartDTO;
import com.example.fishingshop.models.ReelsCart;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private String address;
}
