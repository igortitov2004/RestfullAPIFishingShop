package com.example.fishingshop.services;

import com.example.fishingshop.DTOs.manufacturer.ManufacturerCreationRequest;
import com.example.fishingshop.DTOs.manufacturer.ManufacturerDTO;

import java.util.List;

public interface ManufacturerService{

    List<ManufacturerDTO> list(String name);
    void add(ManufacturerCreationRequest request);
    void delete(Long id);
    void edit(ManufacturerDTO dto);

    ManufacturerDTO getById(Long id);

}
