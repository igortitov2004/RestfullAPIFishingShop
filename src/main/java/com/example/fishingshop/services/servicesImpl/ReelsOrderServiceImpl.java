package com.example.fishingshop.services.servicesImpl;


import com.example.fishingshop.DTOs.orders.ReelsForOrderResponse;
import com.example.fishingshop.DTOs.orders.ReelsOrderDTO;
import com.example.fishingshop.DTOs.reelsCart.ReelCartResponse;
import com.example.fishingshop.DTOs.reelsCart.ReelsCartDTO;
import com.example.fishingshop.exceptions.reelsOrderExceptions.ReelsOrderIsNotExistsException;
import com.example.fishingshop.interfaces.Map;
import com.example.fishingshop.models.Order;
import com.example.fishingshop.models.ReelsOrder;
import com.example.fishingshop.models.RodsOrder;
import com.example.fishingshop.repositories.ReelsOrderRepository;
import com.example.fishingshop.services.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReelsOrderServiceImpl implements Map<ReelsOrderDTO, ReelsOrder>, ReelsOrderService {

    private final ModelMapper modelMapper;
    private final ReelsOrderRepository reelsOrderRepository;
    private final ReelsCartService reelsCartService;

    @Override
    public List<ReelsForOrderResponse> listByOrderId(Long id) {
        return reelsOrderRepository.findReelsOrderByOrderId(id)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public void add(Order order) {
        for(ReelCartResponse response:reelsCartService.listByUserId(order.getUser().getId())){
            ReelsOrderDTO reelsOrderDTO = new ReelsOrderDTO();
            reelsOrderDTO.setReel(response.getReel());
            reelsOrderDTO.setAmount(response.getAmount());
            ReelsOrder reelsOrder = mapToEntity(reelsOrderDTO);
            reelsOrder.setOrder(order);
            reelsOrderRepository.save(reelsOrder);
        }
        reelsCartService.deleteByUserId(order.getUser().getId());
    }
    @Override
    public ReelsOrderDTO mapToDTO(ReelsOrder entity) {
        return modelMapper.map(entity,ReelsOrderDTO.class);
    }
    @Override
    public ReelsOrder mapToEntity(ReelsOrderDTO dto) {
        return modelMapper.map(dto,ReelsOrder.class);
    }

    public ReelsForOrderResponse mapToResponse(ReelsOrder entity){
        return modelMapper.map(entity,ReelsForOrderResponse.class);
    }
}
