package com.example.fishingshop.services;

import com.example.fishingshop.DTOs.RodsCartDTO;
import com.example.fishingshop.DTOs.RodsOrderDTO;

import java.util.List;

public interface RodsOrderService {
    List<RodsOrderDTO> listByUserId(Long id);

    void deleteByUserId(Long id);

    void add(RodsOrderDTO dto);

    RodsOrderDTO getById(Long id);
}
