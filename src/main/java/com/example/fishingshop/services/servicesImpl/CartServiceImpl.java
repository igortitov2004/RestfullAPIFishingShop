package com.example.fishingshop.services.servicesImpl;

import com.example.fishingshop.DTOs.carts.CartResponse;
import com.example.fishingshop.DTOs.carts.reelsCart.ReelForCartResponse;
import com.example.fishingshop.DTOs.carts.rodsCart.RodForCartResponse;
import com.example.fishingshop.exceptions.reelsCartExceptions.ReelsCartIsNotExistsException;
import com.example.fishingshop.exceptions.rodsCartExceptions.RodsCartIsNotExistsException;
import com.example.fishingshop.models.Reel;
import com.example.fishingshop.repositories.ImageReelLinkRepository;
import com.example.fishingshop.services.CartService;
import com.example.fishingshop.services.ReelsCartService;
import com.example.fishingshop.services.RodsCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final ReelsCartService reelsCartService;
    private final RodsCartService rodsCartService;
    private final ImageReelLinkRepository imageReelLinkRepository;

    public CartResponse cart(Long userId) {
        List<RodForCartResponse> rodForCartResponseList;
        List<ReelForCartResponse> reelForCartResponseList;
        double totalReelsPrice = 0d;
        double totalRodsPrice = 0d;
        try{
            reelForCartResponseList = reelsCartService.listByUserId(userId);
            for (ReelForCartResponse response:reelForCartResponseList) {
                totalReelsPrice += response.getReel().getPrice()*response.getAmount().doubleValue();
            }
        }catch (ReelsCartIsNotExistsException e){
            reelForCartResponseList=null;
        }
        try{
            rodForCartResponseList = rodsCartService.listByUserId(userId);
            for (RodForCartResponse response:rodForCartResponseList) {
                totalRodsPrice += response.getRod().getPrice()*response.getAmount().doubleValue();
            }
        }catch (RodsCartIsNotExistsException e){
            rodForCartResponseList = null;
        }
        CartResponse cartResponse = new CartResponse();
        cartResponse.setReelForCartResponseList(reelForCartResponseList);
        cartResponse.setRodForCartResponseList(rodForCartResponseList);
        cartResponse.setTotalPrice(totalReelsPrice+totalRodsPrice);
        return cartResponse;
    }
}
