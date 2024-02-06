package com.example.fishingshop.services;

import com.example.fishingshop.DTOs.reelsCart.ReelsCartDTO;
import com.example.fishingshop.DTOs.reelsCart.ReelsCartCreationRequest;

import java.util.List;

public interface ReelsCartService {

    List<ReelsCartDTO> listByUserId(Long id);
    void deleteById(Long id);
    void deleteByUserId(Long id);
    void add(ReelsCartCreationRequest reelsCartCreationRequest);

    void edit(ReelsCartDTO dto);
}
