package com.example.fishingshop.services.servicesImpl;

import com.example.fishingshop.DTOs.orders.OrderRequest;
import com.example.fishingshop.models.Order;
import com.example.fishingshop.repositories.OrderRepository;
import com.example.fishingshop.services.OrderService;
import com.example.fishingshop.services.ReelsOrderService;
import com.example.fishingshop.services.RodsOrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final ModelMapper modelMapper;

    private final ReelsOrderService reelsOrderService;
    private final RodsOrderService rodsOrderService;

    private final OrderRepository orderRepository;


    @Override
    public Order add(String address) {
        Order order = new Order();
        order.setAddress(address);
//        order.setUser();
//        order.setLocalDateTime();
        return orderRepository.save(order);
    }
}
