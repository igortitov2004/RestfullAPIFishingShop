package com.example.fishingshop.controllers;

import com.example.fishingshop.DTOs.user.ChangePasswordRequest;
import com.example.fishingshop.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/users")
@PreAuthorize("hasAnyRole('USER','ADMIN')")
public class UserController {
    private final UserService service;

    @PreAuthorize("hasAnyAuthority('user:update','admin:update')")
    @PatchMapping
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request,
            Principal connectedUser
    ) {
        service.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }
}
