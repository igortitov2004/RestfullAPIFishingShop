package com.example.fishingshop.services;


import com.example.fishingshop.DTOs.RodDTO;

import java.util.List;

public interface RodService {
    List<RodDTO> list(String name);
    void add(RodDTO dto);
    void delete(Long id);
    void edit(RodDTO dto);
    RodDTO getById(Long id);
}
