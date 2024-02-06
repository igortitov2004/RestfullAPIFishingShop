package com.example.fishingshop.controllers;

import com.example.fishingshop.DTOs.orders.OrderRequest;
import com.example.fishingshop.models.Order;
import com.example.fishingshop.services.OrderService;
import com.example.fishingshop.services.ReelsOrderService;
import com.example.fishingshop.services.RodsOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final ReelsOrderService reelsOrderService;
    private final RodsOrderService rodsOrderService;
    private final OrderService orderService;
    @PutMapping("/")
    public ResponseEntity<String> add(@RequestBody OrderRequest orderRequest){
        Order order = orderService.add(orderRequest.getAddress());
        reelsOrderService.add(orderRequest.getReelsCartDTOList(),order);
        rodsOrderService.add(orderRequest.getRodsCartDTOList(),order);
        return ResponseEntity.ok("Заказ оформлен");
    }
}
