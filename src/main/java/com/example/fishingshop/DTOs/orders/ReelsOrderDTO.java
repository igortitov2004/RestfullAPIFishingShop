package com.example.fishingshop.DTOs.orders;

import com.example.fishingshop.DTOs.reel.ReelDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ReelsOrderDTO {

    private Long id;

    private ReelDTO reel;

    private Integer amount;


}
