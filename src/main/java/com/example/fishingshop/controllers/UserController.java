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
@CrossOrigin(origins = { "http://localhost:3000" })
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasAnyAuthority('USER','ADMIN')")
@RequestMapping("/users")
public class UserController {
    private final UserService service;


    @PatchMapping
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request
    ) {
        service.changePassword(request);
        return ResponseEntity.ok().build();
    }
}
