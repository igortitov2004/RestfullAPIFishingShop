package com.example.fishingshop.services;

import com.example.fishingshop.DTOs.RodsCartDTO;
import com.example.fishingshop.models.RodsCart;

import java.util.List;

public interface RodsCartService {
    List<RodsCartDTO> getList(Long id);


}
