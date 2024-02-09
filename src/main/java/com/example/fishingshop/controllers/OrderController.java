package com.example.fishingshop.controllers;

import com.example.fishingshop.DTOs.orders.OrderRequest;
import com.example.fishingshop.DTOs.orders.OrderResponse;
import com.example.fishingshop.models.Order;
import com.example.fishingshop.services.OrderService;
import com.example.fishingshop.services.ReelsOrderService;
import com.example.fishingshop.services.RodsOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController{
    private final ReelsOrderService reelsOrderService;
    private final RodsOrderService rodsOrderService;
    private final OrderService orderService;
    @GetMapping("/{id}")
    public ResponseEntity<List<OrderResponse>> list(@PathVariable Long id){
        List<OrderResponse> orderResponselist = orderService.list(id);
        return ResponseEntity.ok(orderResponselist);
    }
    @PutMapping("/")
    public ResponseEntity<String> add(@RequestBody OrderRequest orderRequest){
        Order order = orderService.add(orderRequest.getAddress());
        return ResponseEntity.ok("Заказ оформлен : дата и время "+order.getLocalDateTime());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        orderService.deleteById(id);
        return ResponseEntity.ok("Удален заказ с id "+id);
    }
}
