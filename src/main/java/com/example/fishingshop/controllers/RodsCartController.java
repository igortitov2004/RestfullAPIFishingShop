package com.example.fishingshop.controllers;

import com.example.fishingshop.DTOs.carts.rodsCart.RodCartIncreaseAmountRequest;
import com.example.fishingshop.DTOs.carts.rodsCart.RodForCartResponse;
import com.example.fishingshop.DTOs.carts.rodsCart.RodsCartCreationRequest;
import com.example.fishingshop.models.User;
import com.example.fishingshop.services.RodsCartService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasAnyRole('USER')")
@RequestMapping("/rodsCarts")
public class RodsCartController {
    private final RodsCartService rodsCartService;
    @PreAuthorize("hasAuthority('user:read')")
    @GetMapping("/user")
    public ResponseEntity<List<RodForCartResponse>> list(Principal principal){
        Long  userId = getCurrentUserId(principal);
        return ResponseEntity.ok(rodsCartService.listByUserId(userId));
    }
    @PreAuthorize("hasAuthority('user:create')")
    @PutMapping("/")
    public ResponseEntity<String> create(@RequestBody RodsCartCreationRequest rodsCartCreationRequest, Principal principal){
        Long  userId = getCurrentUserId(principal);
        rodsCartService.add(rodsCartCreationRequest,userId);
        return ResponseEntity.ok("Товар добавлен в корзину");
    }
    @PreAuthorize("hasAuthority('user:delete')")
    @DeleteMapping("/user")
    public ResponseEntity<String> deleteByUserId(Principal principal){
        Long  userId = getCurrentUserId(principal);
        rodsCartService.deleteByUserId(userId);
        return ResponseEntity.ok("Удилища c id пользователя" + userId +" удалены из корзины");
    }
    @PreAuthorize("hasAuthority('user:delete')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        rodsCartService.deleteById(id);
        return ResponseEntity.ok("Удилище c id " + id+" удалено из корзины");
    }
    @PreAuthorize("hasAuthority('user:update')")
    @PutMapping("/edit")
    public ResponseEntity<String> update(@RequestBody RodCartIncreaseAmountRequest request){
        rodsCartService.increaseAmount(request);
        return ResponseEntity.ok("Обновлены данные удилища с id " + request.getId());
    }
    private Long getCurrentUserId(Principal principal){
        User user = (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        return user.getId();
    }
}
