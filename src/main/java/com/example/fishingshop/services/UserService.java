package com.example.fishingshop.services;

import com.example.fishingshop.DTOs.user.UserDTO;
import com.example.fishingshop.models.User;

public interface UserService {
    UserDTO getById(Long id);

    void create(User user);
}
