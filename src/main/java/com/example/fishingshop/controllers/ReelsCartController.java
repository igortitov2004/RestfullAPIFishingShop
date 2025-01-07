package com.example.fishingshop.controllers;


import com.example.fishingshop.DTOs.carts.reelsCart.ReelForCartResponse;
import com.example.fishingshop.DTOs.carts.reelsCart.ReelsCartCreationRequest;
import com.example.fishingshop.DTOs.carts.reelsCart.ReelsCartEditAmountRequest;
import com.example.fishingshop.models.User;
import com.example.fishingshop.security.auth.UserVODetails;
import com.example.fishingshop.services.ReelsCartService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = { "http://localhost:3000" })
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasAuthority('USER')")
@RequestMapping("/reelsCarts")
public class ReelsCartController {
    private final ReelsCartService reelsCartService;

    @GetMapping("/")
    public ResponseEntity<List<ReelForCartResponse>> list(Principal principal){
        Long userId = getCurrentUserId(principal);
        return ResponseEntity.ok(reelsCartService.listByUserId(userId));
    }

    @PostMapping("/")
    public ResponseEntity<String> create(@RequestBody ReelsCartCreationRequest request, Principal principal){
        Long userId = getCurrentUserId(principal);
        reelsCartService.add(request,userId);
        return ResponseEntity.ok("Reel with id "+request.getReelId()+" added to cart");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        reelsCartService.deleteById(id);
        return ResponseEntity.ok("Reels cart with id " + id +" deleted from cart");
    }

    @DeleteMapping("/user")
    public ResponseEntity<String> deleteByUserId(Principal principal){
        Long userId = getCurrentUserId(principal);
        reelsCartService.deleteByUserId(userId);
        return ResponseEntity.ok("Reels cart(s) with user id " + userId +" deleted from cart");
    }

    @PutMapping("/edit")
    public ResponseEntity<String> increase(@RequestBody ReelsCartEditAmountRequest request){
        reelsCartService.increaseAmount(request);
        return ResponseEntity.ok("Data of reel with id " + request.getId() + " was updated");
    }

    @PutMapping("/edit/dec")
    public ResponseEntity<String> decrease(@RequestBody ReelsCartEditAmountRequest request){
        reelsCartService.decreaseAmount(request);
        return ResponseEntity.ok("Data of reel with id " + request.getId() + " was updated");
    }
    private Long getCurrentUserId(Principal principal){
        User user = ((UserVODetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        return user.getId();
    }
}
