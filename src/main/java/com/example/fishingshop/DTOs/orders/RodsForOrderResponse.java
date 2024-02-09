package com.example.fishingshop.DTOs.orders;

import com.example.fishingshop.DTOs.rod.RodDTO;
import lombok.Data;

@Data
public class RodsForOrderResponse {
   private RodDTO rodDTO;
   private Integer amount;
}
