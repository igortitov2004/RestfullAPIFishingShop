package com.example.fishingshop.controllers;

import com.example.fishingshop.DTOs.orders.OrderRequest;
import com.example.fishingshop.DTOs.orders.OrderResponse;
import com.example.fishingshop.models.Order;
import com.example.fishingshop.models.User;
import com.example.fishingshop.services.OrderService;
import com.example.fishingshop.services.ReelsOrderService;
import com.example.fishingshop.services.RodsOrderService;
import com.example.fishingshop.services.servicesImpl.OrderServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasAnyRole('USER')")
@RequestMapping("/orders")
public class OrderController{
    private final OrderService orderService;

    @PreAuthorize("hasAuthority('user:read')")
    @GetMapping("/")
    public ResponseEntity<List<OrderResponse>> list(Principal principal){
        User user = getCurrentUser(principal);
        List<OrderResponse> orderResponselist = orderService.list(user.getId());
        return ResponseEntity.ok(orderResponselist);
    }
    @PreAuthorize("hasAuthority('user:create')")
    @PutMapping("/")
    public ResponseEntity<String> create(@RequestBody OrderRequest orderRequest, Principal principal){
        User user = getCurrentUser(principal);
        Order order = orderService.add(orderRequest.getAddress(),user);
        return ResponseEntity.ok("Order is processed: date and time " + order.getLocalDateTime());
    }
    @PreAuthorize("hasAuthority('user:delete')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        orderService.deleteById(id);
        return ResponseEntity.ok("Order with id " + id + " was deleted");
    }
    private User getCurrentUser(Principal principal){
        return (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
    }
}
