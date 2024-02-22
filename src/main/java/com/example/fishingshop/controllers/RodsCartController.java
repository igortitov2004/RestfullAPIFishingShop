package com.example.fishingshop.controllers;

import com.example.fishingshop.DTOs.carts.rodsCart.RodCartIncreaseAmountRequest;
import com.example.fishingshop.DTOs.carts.rodsCart.RodForCartResponse;
import com.example.fishingshop.DTOs.carts.rodsCart.RodsCartCreationRequest;
import com.example.fishingshop.models.RodsCart;
import com.example.fishingshop.models.User;
import com.example.fishingshop.services.RodsCartService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasRole('USER')")
@RequestMapping("/rodsCarts")
public class RodsCartController {
    private final RodsCartService rodsCartService;
    @PreAuthorize("hasAuthority('user:read')")
    @GetMapping("/")
    public ResponseEntity<List<RodForCartResponse>> list(Principal principal){
        Long  userId = getCurrentUserId(principal);
        return ResponseEntity.ok(rodsCartService.listByUserId(userId));
    }
    @PreAuthorize("hasAuthority('user:create')")
    @PostMapping("/")
    public ResponseEntity<String> create(@RequestBody RodsCartCreationRequest request, Principal principal){
        Long  userId = getCurrentUserId(principal);
        rodsCartService.add(request,userId);
        return ResponseEntity.ok("Rod with id "+ request.getRodId() + " added to cart");
    }
    @PreAuthorize("hasAuthority('user:delete')")
    @DeleteMapping("/user")
    public ResponseEntity<String> deleteByUserId(Principal principal){
        Long  userId = getCurrentUserId(principal);
        rodsCartService.deleteByUserId(userId);
        return ResponseEntity.ok("Rods cart(s) with user id " + userId +" deleted from cart");
    }
    @PreAuthorize("hasAuthority('user:delete')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        rodsCartService.deleteById(id);
        return ResponseEntity.ok("Rods cart with id " + id +" deleted from cart");
    }
    @PreAuthorize("hasAuthority('user:update')")
    @PutMapping("/edit")
    public ResponseEntity<String> update(@RequestBody RodCartIncreaseAmountRequest request){
        rodsCartService.increaseAmount(request);
        return ResponseEntity.ok("Data of rod with id " + request.getId() + " was updated");
    }
    private Long getCurrentUserId(Principal principal){
        User user = (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        return user.getId();
    }
}
