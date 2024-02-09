package com.example.fishingshop.services;

import com.example.fishingshop.DTOs.typeOfRod.TypeOfRodCreationRequest;
import com.example.fishingshop.DTOs.typeOfRod.TypeOfRodDTO;

import java.util.List;

public interface TypeOfRodService {

    List<TypeOfRodDTO> list(String name);
    void add(TypeOfRodCreationRequest request);
    void delete(Long id);
    void edit(TypeOfRodDTO dto);

    TypeOfRodDTO getById(Long id);
}
