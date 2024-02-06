package com.example.fishingshop.services;

import com.example.fishingshop.DTOs.rodsCart.RodsCartDTO;
import com.example.fishingshop.DTOs.rodsCart.RodsCartCreationRequest;

import java.util.List;

public interface RodsCartService {
    List<RodsCartDTO> listByUserId(Long id);
    void deleteById(Long id);
    void deleteByUserId(Long id);
    void add(RodsCartCreationRequest request);

    void edit(RodsCartDTO dto);



}
