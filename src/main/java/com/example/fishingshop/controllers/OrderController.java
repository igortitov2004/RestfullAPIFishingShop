package com.example.fishingshop.controllers;

import com.example.fishingshop.DTOs.orders.OrderRequest;
import com.example.fishingshop.DTOs.orders.OrderResponse;
import com.example.fishingshop.models.Order;
import com.example.fishingshop.models.User;
import com.example.fishingshop.security.auth.UserVODetails;
import com.example.fishingshop.services.OrderService;
import com.example.fishingshop.services.ReelsOrderService;
import com.example.fishingshop.services.RodsOrderService;
import com.example.fishingshop.services.servicesImpl.OrderServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000"})
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasAuthority('USER')")
@RequestMapping("/orders")
public class OrderController{
    private final OrderService orderService;

    @GetMapping("/")
    public ResponseEntity<List<OrderResponse>> list(){
        User user = getCurrentUser();
        List<OrderResponse> orderResponselist = orderService.list(user.getId());
        return ResponseEntity.ok(orderResponselist);
    }

    @PutMapping("/")
    public ResponseEntity<String> create(@Valid @RequestBody OrderRequest orderRequest){
        User user = getCurrentUser();
        Order order = orderService.add(orderRequest.getAddress(),user);
        return ResponseEntity.ok("Order is processed: date and time " + order.getLocalDateTime());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        orderService.deleteById(id);
        return ResponseEntity.ok("Order with id " + id + " was deleted");
    }
    private User getCurrentUser(){
        return ((UserVODetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
    }
}
