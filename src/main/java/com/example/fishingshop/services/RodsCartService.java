package com.example.fishingshop.services;

import com.example.fishingshop.DTOs.RodsCartDTO;

import java.util.List;

public interface RodsCartService {
    List<RodsCartDTO> listByUserId(Long id);
    void delete(Long id);
    void add(RodsCartDTO dto);

    void edit(RodsCartDTO dto);

    RodsCartDTO getById(Long id);

}
