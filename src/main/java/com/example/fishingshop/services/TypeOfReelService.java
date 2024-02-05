package com.example.fishingshop.services;

import com.example.fishingshop.DTOs.TypeOfReelDTO;
import com.example.fishingshop.DTOs.TypeOfRodDTO;

import java.util.List;

public interface TypeOfReelService {
    List<TypeOfReelDTO> list(String name);
    void add(TypeOfReelDTO dto);
    void delete(Long id);
    void edit(TypeOfReelDTO dto);
    TypeOfReelDTO getById(Long id);
}
