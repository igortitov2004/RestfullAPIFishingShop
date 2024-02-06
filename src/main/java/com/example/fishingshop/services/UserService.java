package com.example.fishingshop.services;

import com.example.fishingshop.DTOs.UserDTO;

public interface UserService {
    UserDTO getById(Long id);
}
