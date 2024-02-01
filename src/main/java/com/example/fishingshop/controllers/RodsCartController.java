package com.example.fishingshop.controllers;

import com.example.fishingshop.DTOs.RodsCartDTO;
import com.example.fishingshop.models.RodsCart;
import com.example.fishingshop.services.RodsCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RodsCartController {
    private final RodsCartService rodsCartService;
    @GetMapping("/{id}")
    public List<RodsCartDTO> rodsCartList(@PathVariable Long id){
       return rodsCartService.getList(id);
    }
}
