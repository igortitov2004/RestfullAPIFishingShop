package com.example.fishingshop.services.servicesImpl;

import com.example.fishingshop.DTOs.carts.CartResponse;
import com.example.fishingshop.DTOs.carts.reelsCart.ReelForCartResponse;
import com.example.fishingshop.DTOs.carts.rodsCart.RodForCartResponse;
import com.example.fishingshop.services.CartService;
import com.example.fishingshop.services.ReelsCartService;
import com.example.fishingshop.services.RodsCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final ReelsCartService reelsCartService;
    private final RodsCartService rodsCartService;

    public CartResponse cart(Long userId) {
        List<ReelForCartResponse> reelForCartResponseList = reelsCartService.listByUserId(userId);
        List<RodForCartResponse> rodForCartResponseList = rodsCartService.listByUserId(userId);
        double totalReelsPrice = 0d;
        double totalRodsPrice = 0d;
        for (ReelForCartResponse response:reelForCartResponseList) {
            totalReelsPrice += response.getReel().getPrice()*response.getAmount().doubleValue();
        }
        for (RodForCartResponse response:rodForCartResponseList) {
            totalRodsPrice += response.getRod().getPrice()*response.getAmount().doubleValue();
        }
        CartResponse cartResponse = new CartResponse();
        cartResponse.setReelForCartResponseList(reelForCartResponseList);
        cartResponse.setRodForCartResponseList(rodForCartResponseList);
        cartResponse.setTotalPrice(totalReelsPrice+totalRodsPrice);
        return cartResponse;
    }
}
