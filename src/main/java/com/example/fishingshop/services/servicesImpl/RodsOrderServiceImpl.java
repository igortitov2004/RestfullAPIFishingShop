package com.example.fishingshop.services.servicesImpl;

import com.example.fishingshop.DTOs.RodsOrderDTO;
import com.example.fishingshop.exceptions.rodsOrderExceptions.RodsOrderIsNotExistsException;
import com.example.fishingshop.interfaces.Map;
import com.example.fishingshop.models.RodsOrder;
import com.example.fishingshop.repositories.RodsOrderRepository;
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

    @Override
    public List<RodsOrderDTO> listByUserId(Long id) {
        if(!rodsOrderRepository.existsRodsOrderByUserId(id)){
            throw new RodsOrderIsNotExistsException("Rods order with this user id is not exists");
        }
        return rodsOrderRepository.findByUserId(id)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public void deleteByUserId(Long id) {
        if(!rodsOrderRepository.existsRodsOrderByUserId(id)){
            throw new RodsOrderIsNotExistsException("Rods order with this user id is not exists");
        }
        rodsOrderRepository.deleteRodsOrderByUserId(id);
    }

    @Override
    public void add(RodsOrderDTO dto){
        //Подумать
        rodsOrderRepository.save(mapToEntity(dto));
    }

    @Override
    public RodsOrderDTO getById(Long id) {
        return null;
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
