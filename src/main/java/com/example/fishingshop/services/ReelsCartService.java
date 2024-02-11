package com.example.fishingshop.services;

import com.example.fishingshop.DTOs.reelsCart.ReelCartResponse;
import com.example.fishingshop.DTOs.reelsCart.ReelsCartDTO;
import com.example.fishingshop.DTOs.reelsCart.ReelsCartCreationRequest;
import com.example.fishingshop.DTOs.reelsCart.ReelsCartIncreaseAmountRequest;
import com.example.fishingshop.models.ReelsCart;

import java.util.List;
import java.util.Optional;

public interface ReelsCartService {

    List<ReelCartResponse> listByUserId(Long id);
    void deleteById(Long id);
    void deleteByUserId(Long id);
    void add(ReelsCartCreationRequest reelsCartCreationRequest);

    void increaseAmount(ReelsCartIncreaseAmountRequest reelsCartIncreaseAmountRequest);

    ReelsCartDTO getById(Long id);

    Optional<ReelsCart> reelCartByUserIdAndReelId(Long userId,Long reelId);
}
