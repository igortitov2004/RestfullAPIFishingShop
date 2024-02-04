package com.example.fishingshop.services;

import com.example.fishingshop.DTOs.ManufacturerDTO;
import com.example.fishingshop.models.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ManufacturerService{

    List<ManufacturerDTO> list(String name);
    void add(ManufacturerDTO dto);
    void delete(Long id);
    void edit(ManufacturerDTO dto);

    ManufacturerDTO getById(Long id);

}
