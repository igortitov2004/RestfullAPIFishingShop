package com.example.fishingshop.services;

import com.example.fishingshop.DTOs.carts.reelsCart.ReelForCartResponse;
import com.example.fishingshop.DTOs.carts.reelsCart.ReelsCartDTO;
import com.example.fishingshop.DTOs.carts.reelsCart.ReelsCartCreationRequest;
import com.example.fishingshop.DTOs.carts.reelsCart.ReelsCartEditAmountRequest;

import java.util.List;

public interface ReelsCartService {

    List<ReelForCartResponse> listByUserId(Long id);
    void deleteById(Long id);
    void deleteByUserId(Long id);
    void add(ReelsCartCreationRequest request, Long userId);

    void increaseAmount(ReelsCartEditAmountRequest request);
    void decreaseAmount(ReelsCartEditAmountRequest request);

    ReelsCartDTO getById(Long id);


}
