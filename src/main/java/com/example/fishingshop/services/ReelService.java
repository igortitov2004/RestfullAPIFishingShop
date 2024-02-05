package com.example.fishingshop.services;

import com.example.fishingshop.DTOs.ReelDTO;
import com.example.fishingshop.DTOs.RodDTO;

import java.util.List;

public interface ReelService {
    List<ReelDTO> list(String name);
    void add(ReelDTO dto);
    void delete(Long id);
    void edit(ReelDTO dto);
    ReelDTO getById(Long id);
}
