package com.example.fishingshop.services.servicesImpl;


import com.example.fishingshop.DTOs.orders.RodsForOrderResponse;
import com.example.fishingshop.DTOs.orders.RodsOrderDTO;
import com.example.fishingshop.DTOs.carts.rodsCart.RodForCartResponse;
import com.example.fishingshop.interfaces.Map;
import com.example.fishingshop.models.Order;
import com.example.fishingshop.models.RodsOrder;
import com.example.fishingshop.repositories.RodsOrderRepository;
import com.example.fishingshop.services.RodsCartService;
import com.example.fishingshop.services.RodsOrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RodsOrderServiceImpl implements Map<RodsOrderDTO, RodsOrder>, RodsOrderService {
    private final ModelMapper modelMapper;
    private final RodsOrderRepository rodsOrderRepository;
    private final RodsCartService rodsCartService;
    @Override
    public List<RodsForOrderResponse> listByOrderId(Long id) {
       return rodsOrderRepository.findRodsOrderByOrderId(id)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    @Override
    public Double add(Order order) {
        double totalRodsPrice=0d;
        for(RodForCartResponse response:rodsCartService.listByUserId(order.getUser().getId())) {
            RodsOrderDTO rodsOrderDTO = new RodsOrderDTO();
            rodsOrderDTO.setRod(response.getRod());
            rodsOrderDTO.setAmount(response.getAmount());
            totalRodsPrice+=response.getRod().getPrice()*response.getAmount().doubleValue();
            RodsOrder rodsOrder = mapToEntity(rodsOrderDTO);
            rodsOrder.setOrder(order);
            rodsOrderRepository.save(rodsOrder);
        }
        rodsCartService.deleteByUserId(order.getUser().getId());
        return totalRodsPrice;
    }
    @Override
    public RodsOrderDTO mapToDTO(RodsOrder entity) {
        return modelMapper.map(entity,RodsOrderDTO.class);
    }
    @Override
    public RodsOrder mapToEntity(RodsOrderDTO dto) {
        return modelMapper.map(dto,RodsOrder.class);
    }

    public RodsForOrderResponse mapToResponse(RodsOrder entity){
        return modelMapper.map(entity,RodsForOrderResponse.class);
    }
}
