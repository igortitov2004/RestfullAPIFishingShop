package com.example.fishingshop.services;


import com.example.fishingshop.DTOs.rod.RodCreationRequest;
import com.example.fishingshop.DTOs.rod.RodDTO;
import com.example.fishingshop.DTOs.rod.RodEditRequest;
import com.example.fishingshop.DTOs.rod.RodsResponse;

import java.util.List;

public interface RodService {
    List<RodsResponse> getList(String name);
    List<RodDTO> list(String name);
    void add(RodCreationRequest dto);
    void delete(Long id);
    void edit(RodEditRequest request);
    RodDTO getById(Long id);
}
