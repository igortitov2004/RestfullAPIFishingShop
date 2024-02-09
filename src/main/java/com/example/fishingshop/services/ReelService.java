package com.example.fishingshop.services;

import com.example.fishingshop.DTOs.reel.ReelCreationRequest;
import com.example.fishingshop.DTOs.reel.ReelDTO;
import com.example.fishingshop.DTOs.reel.ReelEditRequest;

import java.util.List;

public interface ReelService {
    List<ReelDTO> list(String name);
    void add(ReelCreationRequest dto);
    void delete(Long id);
    void edit(ReelEditRequest request);
    ReelDTO getById(Long id);
}
