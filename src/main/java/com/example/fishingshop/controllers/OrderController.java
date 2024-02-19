package com.example.fishingshop.controllers;

import com.example.fishingshop.DTOs.orders.OrderRequest;
import com.example.fishingshop.DTOs.orders.OrderResponse;
import com.example.fishingshop.models.Order;
import com.example.fishingshop.services.OrderService;
import com.example.fishingshop.services.ReelsOrderService;
import com.example.fishingshop.services.RodsOrderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasAnyRole('USER')")
@RequestMapping("/orders")
public class OrderController{
    private final OrderService orderService;

    @PreAuthorize("hasAuthority('user:read')")
    @GetMapping("/{id}")
    public ResponseEntity<List<OrderResponse>> list(@PathVariable Long id){
        List<OrderResponse> orderResponselist = orderService.list(id);
        return ResponseEntity.ok(orderResponselist);
    }
    @PreAuthorize("hasAuthority('user:create')")
    @PutMapping("/")
    public ResponseEntity<String> create(@RequestBody OrderRequest orderRequest){
        Order order = orderService.add(orderRequest.getAddress());
        return ResponseEntity.ok("Заказ оформлен : дата и время "+order.getLocalDateTime());
    }
    @PreAuthorize("hasAuthority('user:delete')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        orderService.deleteById(id);
        return ResponseEntity.ok("Удален заказ с id "+id);
    }
}
