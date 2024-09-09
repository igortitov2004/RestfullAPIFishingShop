package com.example.fishingshop.security.auth;

import com.example.fishingshop.security.jwt.JwtService;
import com.example.fishingshop.models.User;
import com.example.fishingshop.repositories.UserRepository;
import com.example.fishingshop.enums.Role;
import com.example.fishingshop.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    //todo remove repo, add service
    private final UserRepository repository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserVODetailsService userVODetailsService;
    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .surname(request.getSurname())
                .firstName(request.getFirstName())
                .patronymic(request.getPatronymic())
                .role(Role.USER)
                .build();
        userService.create(user);
        var jwtToken = jwtService.generateToken(new UserVODetails(user));
        return AuthenticationResponse.builder().token(jwtToken)
                .role(Role.USER.toString())
                .username(request.getEmail())
                .build();
    }
    public AuthenticationResponse authenticate(AuthenticationRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userVODetailsService.loadUserByUsername(request.getEmail());
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .refreshToken(refreshToken)
                .role(user.getAuthorities().stream().toList().get(0).toString())
                .username(user.getUsername()).build();
    }
    @SneakyThrows
    public void refreshToken(HttpServletRequest request,
                             HttpServletResponse response) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String username;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        username = jwtService.extractUsername(refreshToken);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            var user =  userVODetailsService.loadUserByUsername(username);
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                var authResponse = AuthenticationResponse.builder()
                        .role(user.getAuthorities().stream().toList().get(0).toString())
                        .token(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
