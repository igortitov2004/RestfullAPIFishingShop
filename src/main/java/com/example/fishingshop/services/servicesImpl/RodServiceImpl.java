package com.example.fishingshop.services.servicesImpl;

import com.example.fishingshop.DTOs.rod.RodCreationRequest;
import com.example.fishingshop.DTOs.rod.RodDTO;
import com.example.fishingshop.DTOs.rod.RodEditRequest;
import com.example.fishingshop.exceptions.rodExceptions.RodIsNotExistException;
import com.example.fishingshop.interfaces.Map;
import com.example.fishingshop.models.Rod;
import com.example.fishingshop.repositories.RodRepository;
import com.example.fishingshop.services.ManufacturerService;
import com.example.fishingshop.services.RodService;
import com.example.fishingshop.services.TypeOfRodService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RodServiceImpl implements Map<RodDTO, Rod>, RodService {
    private final ModelMapper modelMapper;
    private final RodRepository rodRepository;
    private final ManufacturerService manufacturerService;
    private final TypeOfRodService typeOfRodService;
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
    public void add(RodCreationRequest dto) {
        RodDTO rodDTO = new RodDTO();
        rodDTO.setName(dto.getName());
        rodDTO.setPrice(dto.getPrice());
        rodDTO.setLength(dto.getLength());
        rodDTO.setTestLoad(dto.getTestLoad());
        rodDTO.setWeight(dto.getWeight());
        rodDTO.setManufacturer(manufacturerService.getById(dto.getManufacturerId()));
        rodDTO.setType(typeOfRodService.getById(dto.getTypeId()));
        rodRepository.save(mapToEntity(rodDTO));
    }
    @Override
    public void delete(Long id) {
        if(!rodRepository.existsRodById(id)){
            throw new RodIsNotExistException("Rod with this id is not exists");
        }
        rodRepository.deleteById(id);
    }
    @Override
    public void edit(RodEditRequest request) {
        RodDTO dto = getById(request.getId());
        dto.setName(request.getName());
        dto.setPrice(request.getPrice());
        dto.setManufacturer(manufacturerService.getById(request.getManufacturerId()));
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
