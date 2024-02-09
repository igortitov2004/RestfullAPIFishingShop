package com.example.fishingshop.services;

import com.example.fishingshop.DTOs.typeOfReel.TypeOfReelCreationRequest;
import com.example.fishingshop.DTOs.typeOfReel.TypeOfReelDTO;

import java.util.List;

public interface TypeOfReelService {
    List<TypeOfReelDTO> list(String name);
    void add(TypeOfReelCreationRequest request);
    void delete(Long id);
    void edit(TypeOfReelDTO dto);
    TypeOfReelDTO getById(Long id);
}
