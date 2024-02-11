package com.example.fishingshop.controllers;

import com.example.fishingshop.DTOs.rodsCart.RodCartIncreaseAmountRequest;
import com.example.fishingshop.DTOs.rodsCart.RodCartResponse;
import com.example.fishingshop.DTOs.rodsCart.RodsCartDTO;
import com.example.fishingshop.DTOs.rodsCart.RodsCartCreationRequest;
import com.example.fishingshop.services.RodsCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rodsCarts")
public class RodsCartController {
    private final RodsCartService rodsCartService;
    @GetMapping("/user/{id}")
    public ResponseEntity<List<RodCartResponse>> list(@PathVariable Long id){
       return ResponseEntity.ok(rodsCartService.listByUserId(id));
    }
    @PutMapping("/")
    public ResponseEntity<String> create(@RequestBody RodsCartCreationRequest rodsCartCreationRequest){
        rodsCartService.add(rodsCartCreationRequest);
        return ResponseEntity.ok("Товар добавлен в корзину");
    }
    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteByUserId(@PathVariable Long id){
        rodsCartService.deleteByUserId(id);
        return ResponseEntity.ok("Удилища c id пользователя" + id+" удалены из корзины");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        rodsCartService.deleteById(id);
        return ResponseEntity.ok("Удилище c id " + id+" удалено из корзины");
    }
    @PutMapping("/edit")
    public ResponseEntity<String> update(@RequestBody RodCartIncreaseAmountRequest request){
        rodsCartService.increaseAmount(request);
        return ResponseEntity.ok("Обновлены данные удилища с id " + request.getId());
    }
}
