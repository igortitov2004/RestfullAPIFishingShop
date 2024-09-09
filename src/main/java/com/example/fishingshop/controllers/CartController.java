package com.example.fishingshop.controllers;

import com.example.fishingshop.DTOs.carts.CartResponse;
import com.example.fishingshop.models.User;
import com.example.fishingshop.security.auth.UserVODetails;
import com.example.fishingshop.services.CartService;
import com.example.fishingshop.services.servicesImpl.CartServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000"})
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasAuthority('USER')")
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;


    @GetMapping()
    public ResponseEntity<CartResponse> list(){
        User user = ((UserVODetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        return ResponseEntity.ok(cartService.cart(user.getId()));
    }
}
