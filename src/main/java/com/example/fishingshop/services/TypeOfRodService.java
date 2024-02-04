package com.example.fishingshop.services;

import com.example.fishingshop.DTOs.ManufacturerDTO;
import com.example.fishingshop.DTOs.TypeOfRodDTO;

import java.util.List;

public interface TypeOfRodService {

    List<TypeOfRodDTO> list(String name);
    void add(TypeOfRodDTO dto);
    void delete(Long id);
    void edit(TypeOfRodDTO dto);

    TypeOfRodDTO getById(Long id);
}
