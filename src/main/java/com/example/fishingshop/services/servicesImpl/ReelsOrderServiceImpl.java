package com.example.fishingshop.services.servicesImpl;


import com.example.fishingshop.DTOs.orders.ReelsForOrderResponse;
import com.example.fishingshop.DTOs.orders.ReelsOrderDTO;
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
    private final ReelService reelService;
    private final ReelsCartService reelsCartService;

    @Override
    public List<ReelsForOrderResponse> listByOrderId(Long id) {
        List<ReelsOrderDTO> reelsOrderDTOList =
                reelsOrderRepository.findReelsOrderByOrderId(id)
                        .stream()
                        .map(this::mapToDTO)
                        .toList();
        List<ReelsForOrderResponse> responseList = new ArrayList<>();
        for (ReelsOrderDTO dto:reelsOrderDTOList){
            responseList.add(modelMapper.map(dto,ReelsForOrderResponse.class));
        }
        return responseList;
    }
    @Override
    public void deleteById(Long id) {
        if(reelsOrderRepository.existsReelsOrderById(id)){
            throw new ReelsOrderIsNotExistsException("Reels order with this id is not exists");
        }
        reelsOrderRepository.deleteById(id);
    }
    public ReelsOrderDTO getById(Long id){
        if(!reelsOrderRepository.existsReelsOrderById(id)){
            throw new ReelsOrderIsNotExistsException("Reels order with this id is not exists");
        }
        return reelsOrderRepository.findById(id).map(this::mapToDTO).get();
    }
    public void add(Order order) {
        for(ReelsCartDTO dto:reelsCartService.listByUserId(1L)){
            ReelsOrderDTO reelsOrderDTO = new ReelsOrderDTO();
            reelsOrderDTO.setReel(dto.getReel());
            reelsOrderDTO.setAmount(dto.getAmount());
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
}
