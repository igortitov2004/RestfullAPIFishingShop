package com.example.fishingshop.controllers;


import com.example.fishingshop.DTOs.carts.reelsCart.ReelForCartResponse;
import com.example.fishingshop.DTOs.carts.reelsCart.ReelsCartCreationRequest;
import com.example.fishingshop.DTOs.carts.reelsCart.ReelsCartIncreaseAmountRequest;
import com.example.fishingshop.models.User;
import com.example.fishingshop.services.ReelsCartService;
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
@CrossOrigin(origins = { "http://localhost:3000" })
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasAnyRole('USER')")
@RequestMapping("/reelsCarts")
public class ReelsCartController {
    private final ReelsCartService reelsCartService;
    @PreAuthorize("hasAuthority('user:read')")
    @GetMapping("/")
    public ResponseEntity<List<ReelForCartResponse>> list(Principal principal){
        Long userId = getCurrentUserId(principal);
        return ResponseEntity.ok(reelsCartService.listByUserId(userId));
    }
    @PreAuthorize("hasAuthority('user:create')")
    @PostMapping("/")
    public ResponseEntity<String> create(@RequestBody ReelsCartCreationRequest request, Principal principal){
        Long userId = getCurrentUserId(principal);
        reelsCartService.add(request,userId);
        return ResponseEntity.ok("Reel with id "+request.getReelId()+" added to cart");
    }
    @PreAuthorize("hasAuthority('user:delete')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        reelsCartService.deleteById(id);
        return ResponseEntity.ok("Reels cart with id " + id +" deleted from cart");
    }
    @PreAuthorize("hasAuthority('user:delete')")
    @DeleteMapping("/user")
    public ResponseEntity<String> deleteByUserId(Principal principal){
        Long userId = getCurrentUserId(principal);
        reelsCartService.deleteByUserId(userId);
        return ResponseEntity.ok("Reels cart(s) with user id " + userId +" deleted from cart");
    }
    @PreAuthorize("hasAuthority('user:update')")
    @PutMapping("/edit")
    public ResponseEntity<String> update(@RequestBody ReelsCartIncreaseAmountRequest request){
        reelsCartService.increaseAmount(request);
        return ResponseEntity.ok("Data of reel with id " + request.getId() + " was updated");
    }
    private Long getCurrentUserId(Principal principal){
        User user = (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        return user.getId();
    }
}
