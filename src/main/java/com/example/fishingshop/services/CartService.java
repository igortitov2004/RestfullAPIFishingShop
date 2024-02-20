package com.example.fishingshop.services;

import com.example.fishingshop.DTOs.carts.CartResponse;

public interface CartService {
    CartResponse cart(Long userId);
}
