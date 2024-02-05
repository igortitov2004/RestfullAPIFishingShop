package com.example.fishingshop.services.servicesImpl;

import com.example.fishingshop.DTOs.RodDTO;
import com.example.fishingshop.exceptions.rodExceptions.RodIsNotExistException;
import com.example.fishingshop.interfaces.Map;
import com.example.fishingshop.models.Rod;
import com.example.fishingshop.repositories.RodRepository;
import com.example.fishingshop.services.RodService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RodServiceImpl implements Map<RodDTO, Rod>, RodService {
    private final ModelMapper modelMapper;
    private final RodRepository rodRepository;
    @Override
    public List<RodDTO> list(String name) {
        if(name!=null){
            return rodRepository.findRodByNameContaining(name)
                    .stream()
                    .map(this::mapToDTO)
                    .toList();
        }
        return rodRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public void add(RodDTO dto) {
        //Valid
        rodRepository.save(mapToEntity(dto));
    }

    @Override
    public void delete(Long id) {
        if(!rodRepository.existsRodById(id)){
            throw new RodIsNotExistException("Rod with this id is not exists");
        }
        rodRepository.deleteById(id);
    }

    @Override
    public void edit(RodDTO dto) {
        if(!rodRepository.existsRodById(dto.getId())){
            throw new RodIsNotExistException("Rod with this id is not exists");
        }
        // Valid
        rodRepository.save(mapToEntity(dto));
    }

    @Override
    public RodDTO getById(Long id) {
        if(!rodRepository.existsRodById(id)){
            throw new RodIsNotExistException("Rod with this id is not exists");
        }
        return rodRepository.findById(id).map(this::mapToDTO).get();
    }
    @Override
    public RodDTO mapToDTO(Rod entity) {
        return modelMapper.map(entity,RodDTO.class);
    }
    @Override
    public Rod mapToEntity(RodDTO dto) {
        return modelMapper.map(dto,Rod.class);
    }


}
