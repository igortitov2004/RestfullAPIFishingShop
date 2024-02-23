package com.example.fishingshop.services;

import com.example.fishingshop.DTOs.user.ChangePasswordRequest;
import com.example.fishingshop.DTOs.user.UserDTO;
import com.example.fishingshop.models.User;

import java.security.Principal;

public interface UserService {
    UserDTO getById(Long id);
    void create(User user);
    void changePassword(ChangePasswordRequest request, Principal connectedUser);
}
