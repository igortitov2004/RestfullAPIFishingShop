package com.example.fishingshop.services.servicesImpl;


import com.example.fishingshop.DTOs.orders.RodsForOrderResponse;
import com.example.fishingshop.DTOs.orders.RodsOrderDTO;
import com.example.fishingshop.DTOs.rodsCart.RodCartResponse;
import com.example.fishingshop.DTOs.rodsCart.RodsCartDTO;
import com.example.fishingshop.exceptions.rodsOrderExceptions.RodsOrderIsNotExistsException;
import com.example.fishingshop.interfaces.Map;
import com.example.fishingshop.models.Order;
import com.example.fishingshop.models.RodsOrder;
import com.example.fishingshop.repositories.RodsOrderRepository;
import com.example.fishingshop.services.RodService;
import com.example.fishingshop.services.RodsCartService;
import com.example.fishingshop.services.RodsOrderService;
import com.example.fishingshop.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RodsOrderServiceImpl implements Map<RodsOrderDTO, RodsOrder>, RodsOrderService {
    private final ModelMapper modelMapper;
    private final RodsOrderRepository rodsOrderRepository;
    private final RodsCartService rodsCartService;
    @Override
    public List<RodsForOrderResponse> listByOrderId(Long id) {
        List<RodsOrderDTO> rodsOrderDTOlist =
                rodsOrderRepository.findRodsOrderByOrderId(id)
                .stream()
                .map(this::mapToDTO)
                .toList();
        List<RodsForOrderResponse> responseList = new ArrayList<>();
        for (RodsOrderDTO dto:rodsOrderDTOlist){
            responseList.add(modelMapper.map(dto,RodsForOrderResponse.class));
        }
        return responseList;
    }
    @Override
    public void deleteById(Long id) {
        if(!rodsOrderRepository.existsRodsOrderById(id)){
            throw new RodsOrderIsNotExistsException("Rods order with this id is not exists");
        }
        rodsOrderRepository.deleteById(id);
    }
    @Override
    public void add(Order order) {
        for(RodCartResponse response:rodsCartService.listByUserId(order.getUser().getId())) {
            RodsOrderDTO rodsOrderDTO = new RodsOrderDTO();
            rodsOrderDTO.setRod(response.getRod());
            rodsOrderDTO.setAmount(response.getAmount());
            RodsOrder rodsOrder = mapToEntity(rodsOrderDTO);
            rodsOrder.setOrder(order);
            rodsOrderRepository.save(rodsOrder);
        }
        rodsCartService.deleteByUserId(order.getUser().getId());
    }
    @Override
    public RodsOrderDTO mapToDTO(RodsOrder entity) {
        return modelMapper.map(entity,RodsOrderDTO.class);
    }
    @Override
    public RodsOrder mapToEntity(RodsOrderDTO dto) {
        return modelMapper.map(dto,RodsOrder.class);
    }


}
