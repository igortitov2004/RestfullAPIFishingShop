package com.example.fishingshop.services.servicesImpl;

import com.example.fishingshop.DTOs.orders.ReelsForOrderRequestDTO;
import com.example.fishingshop.DTOs.orders.ReelsOrderDTO;
import com.example.fishingshop.DTOs.orders.RodsForOrderRequestDTO;
import com.example.fishingshop.DTOs.orders.RodsOrderDTO;
import com.example.fishingshop.exceptions.rodsOrderExceptions.RodsOrderIsNotExistsException;
import com.example.fishingshop.interfaces.Map;
import com.example.fishingshop.models.Order;
import com.example.fishingshop.models.RodsOrder;
import com.example.fishingshop.repositories.RodsOrderRepository;
import com.example.fishingshop.services.RodService;
import com.example.fishingshop.services.RodsOrderService;
import com.example.fishingshop.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RodsOrderServiceImpl implements Map<RodsOrderDTO, RodsOrder>, RodsOrderService {
    private final ModelMapper modelMapper;

    private final RodsOrderRepository rodsOrderRepository;

    private final RodService rodService;
    private final UserService userService;

    @Override
    public List<RodsOrderDTO> listByUserId(Long id) {
      return null;
    }

    @Override
    public void deleteByUserId(Long id) {

    }

    @Override
    public void deleteById(Long id) {
        if(!rodsOrderRepository.existsRodsOrderById(id)){
            throw new RodsOrderIsNotExistsException("Rods order with this id is not exists");
        }
        rodsOrderRepository.deleteById(id);
    }

    public void add(List<RodsForOrderRequestDTO> rodsForOrderRequestDTOList, Order order) {
        for (RodsForOrderRequestDTO dto:rodsForOrderRequestDTOList) {
            RodsOrderDTO rodsOrderDTO = new RodsOrderDTO();
            rodsOrderDTO.setRod(rodService.getById(dto.getIdRods()));
            rodsOrderDTO.setAmount(dto.getAmount());
            RodsOrder rodsOrder = mapToEntity(rodsOrderDTO);
            rodsOrder.setOrder(order);
            rodsOrderRepository.save(rodsOrder);
        }
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
