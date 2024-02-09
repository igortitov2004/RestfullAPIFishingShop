package com.example.fishingshop.services;

import com.example.fishingshop.DTOs.rodsCart.RodCartIncreaseAmountRequest;
import com.example.fishingshop.DTOs.rodsCart.RodsCartDTO;
import com.example.fishingshop.DTOs.rodsCart.RodsCartCreationRequest;
import com.example.fishingshop.models.RodsCart;

import java.util.List;
import java.util.Optional;

public interface RodsCartService {
    List<RodsCartDTO> listByUserId(Long id);
    void deleteById(Long id);
    void deleteByUserId(Long id);
    void add(RodsCartCreationRequest request);

    void increaseAmount(RodCartIncreaseAmountRequest request);
    RodsCartDTO getById(Long id);

    Optional<RodsCart> reelCartByUserIdAndRodId(Long userId, Long rodId);
}
