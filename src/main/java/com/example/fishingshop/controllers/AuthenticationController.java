package com.example.fishingshop.controllers;

import com.example.fishingshop.security.auth.AuthenticationRequest;
import com.example.fishingshop.security.auth.AuthenticationResponse;
import com.example.fishingshop.security.auth.AuthenticationService;
import com.example.fishingshop.security.auth.RegisterRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@CrossOrigin(origins = {"http://localhost:3000"})
@SecurityRequirement(name = "bearerAuth")
public class AuthenticationController {
    private final AuthenticationService service;

    @Operation(
            description = "Get endpoint for registration",
            summary = "registration",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token / No access",
                            responseCode = "403"
                    )
            }
    )
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ){
       return ResponseEntity.ok(service.register(request));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> auth(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(service.authenticate(request));
    }
    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request,
                             HttpServletResponse response){
        service.refreshToken(request, response);
    }
}
