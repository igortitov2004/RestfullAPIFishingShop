package com.example.fishingshop.services;

import com.example.fishingshop.DTOs.carts.rodsCart.RodCartEditAmountRequest;
import com.example.fishingshop.DTOs.carts.rodsCart.RodForCartResponse;
import com.example.fishingshop.DTOs.carts.rodsCart.RodsCartDTO;
import com.example.fishingshop.DTOs.carts.rodsCart.RodsCartCreationRequest;
import com.example.fishingshop.models.RodsCart;

import java.util.List;
import java.util.Optional;

public interface RodsCartService {
    List<RodForCartResponse> listByUserId(Long id);
    void deleteById(Long id);
    void deleteByUserId(Long id);


    void add(RodsCartCreationRequest request, Long userId);

    void increaseAmount(RodCartEditAmountRequest request);
    void decreaseAmount(RodCartEditAmountRequest request);
    RodsCartDTO getById(Long id);

    Optional<RodsCart> rodsCartByUserIdAndRodId(Long userId, Long rodId);
}
