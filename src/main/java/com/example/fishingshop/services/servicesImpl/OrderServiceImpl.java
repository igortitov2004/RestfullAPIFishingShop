package com.example.fishingshop.services.servicesImpl;

import com.example.fishingshop.DTOs.orders.OrderDTO;
import com.example.fishingshop.DTOs.orders.OrderRequest;
import com.example.fishingshop.DTOs.orders.OrderResponse;
import com.example.fishingshop.exceptions.orderExceptions.OrderIsNotExistsException;
import com.example.fishingshop.interfaces.Map;
import com.example.fishingshop.models.Order;
import com.example.fishingshop.repositories.OrderRepository;
import com.example.fishingshop.repositories.UserRepository;
import com.example.fishingshop.services.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements Map<OrderDTO,Order>,OrderService {
    private final ModelMapper modelMapper;



    private final OrderRepository orderRepository;

    private final ReelsOrderService reelsOrderService;
    private final RodsOrderService rodsOrderService;
    private final UserRepository userRepository;

    @Override
    public Order add(String address) {
        Order order = new Order();
        order.setAddress(address);
        order.setUser(userRepository.findById(2L).get());
        order=orderRepository.save(order);
        reelsOrderService.add(order);
        rodsOrderService.add(order);
        return order;
    }

    @Override
    public List<OrderResponse> list(Long userId) {
        if(!orderRepository.existsOrderByUserId(userId)){
            throw new OrderIsNotExistsException("Order with this user id is not exists");
        }
        List<OrderDTO> orderDTOList=orderRepository.findOrderByUserId(userId)
                .stream()
                .map(this::mapToDTO)
                .toList();
        List<OrderResponse> orderResponseList = new ArrayList<>();
        for (OrderDTO orderDTO:orderDTOList){
            OrderResponse orderResponse = new OrderResponse();
            orderResponse.setReelsForOrderResponseList(reelsOrderService.listByOrderId(orderDTO.getId()));
            orderResponse.setRodsForOrderResponseList(rodsOrderService.listByOrderId(orderDTO.getId()));
            orderResponseList.add(orderResponse);
        }
        return orderResponseList;
    }

    @Override
    public void deleteById(Long id){
        if(!orderRepository.existsOrderById(id)){
            throw new OrderIsNotExistsException("Order with this id is not exists");
        }
//        rodsOrderService.deleteByOrderId(id);
//        reelsOrderService.deleteByOrderId(id);
        orderRepository.deleteById(id);
    }


    @Override
    public OrderDTO mapToDTO(Order entity) {
        return modelMapper.map(entity,OrderDTO.class);
    }

    @Override
    public Order mapToEntity(OrderDTO dto) {
        return modelMapper.map(dto,Order.class);
    }
}
