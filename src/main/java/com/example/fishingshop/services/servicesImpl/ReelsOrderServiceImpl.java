package com.example.fishingshop.services.servicesImpl;

import com.example.fishingshop.DTOs.orders.ReelsForOrderRequestDTO;
import com.example.fishingshop.DTOs.orders.ReelsOrderDTO;
import com.example.fishingshop.exceptions.reelsOrderExceptions.ReelsOrderIsNotExistsException;
import com.example.fishingshop.interfaces.Map;
import com.example.fishingshop.models.Order;
import com.example.fishingshop.models.ReelsOrder;
import com.example.fishingshop.models.RodsOrder;
import com.example.fishingshop.repositories.ReelsOrderRepository;
import com.example.fishingshop.services.ReelService;
import com.example.fishingshop.services.ReelsOrderService;
import com.example.fishingshop.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReelsOrderServiceImpl implements Map<ReelsOrderDTO, ReelsOrder>, ReelsOrderService {

    private final ModelMapper modelMapper;
    private final ReelsOrderRepository reelsOrderRepository;
    private final ReelService reelService;

    @Override
    public List<ReelsOrderDTO> listByUserId(Long id) {
        return null;
    }

    @Override
    public void deleteByUserId(Long id) {

    }

    @Override
    public void deleteById(Long id) {
        if(!reelsOrderRepository.existsReelsOrderById(id)){
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



    public void add(List<ReelsForOrderRequestDTO> reelsForOrderRequestDTOList, Order order) {
        for (ReelsForOrderRequestDTO dto:reelsForOrderRequestDTOList) {
            ReelsOrderDTO reelsOrderDTO = new ReelsOrderDTO();
            reelsOrderDTO.setReel(reelService.getById(dto.getIdReels()));
            reelsOrderDTO.setAmount(dto.getAmount());
            ReelsOrder reelsOrder = mapToEntity(reelsOrderDTO);
            reelsOrder.setOrder(order);
            reelsOrderRepository.save(reelsOrder);
        }
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
