package com.example.fishingshop.services.servicesImpl;


import com.example.fishingshop.DTOs.reel.ReelCreationRequest;
import com.example.fishingshop.DTOs.reel.ReelDTO;
import com.example.fishingshop.DTOs.reel.ReelEditRequest;
import com.example.fishingshop.exceptions.reelExceptions.ReelIsNotExistsException;
import com.example.fishingshop.interfaces.Map;
import com.example.fishingshop.models.Reel;
import com.example.fishingshop.repositories.ReelRepository;
import com.example.fishingshop.services.ManufacturerService;
import com.example.fishingshop.services.ReelService;
import com.example.fishingshop.services.TypeOfReelService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReelServiceImpl implements Map<ReelDTO, Reel>, ReelService {

    private final ModelMapper modelMapper;

    private final ReelRepository reelRepository;

    private final ManufacturerService manufacturerService;
    private final TypeOfReelService typeOfReelService;


    @Override
    public List<ReelDTO> list(String name) {
        if(name!=null){
            return reelRepository.findReelByNameContaining(name)
                    .stream()
                    .map(this::mapToDTO)
                    .toList();
        }
        return reelRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public void add(ReelCreationRequest dto) {
        ReelDTO reelDTO = new ReelDTO();
        reelDTO.setName(dto.getName());
        reelDTO.setPrice(dto.getPrice());
        reelDTO.setType(typeOfReelService.getById(dto.getTypeId()));
        reelDTO.setManufacturer(manufacturerService.getById(dto.getManufacturerId()));
        reelRepository.save(mapToEntity(reelDTO));
    }

    @Override
    public void delete(Long id) {
        if(!reelRepository.existsReelById(id)){
            throw new ReelIsNotExistsException("Reel with this id is not exists");
        }
        reelRepository.deleteById(id);
    }

    @Override
    public void edit(ReelEditRequest request) {
        ReelDTO dto = getById(request.getId());
        dto.setName(request.getName());
        dto.setPrice(dto.getPrice());
        dto.setManufacturer(manufacturerService.getById(request.getManufacturerId()));
        reelRepository.save(mapToEntity(dto));
    }

    @Override
    public ReelDTO getById(Long id) {
        if(!reelRepository.existsReelById(id)){
            throw new ReelIsNotExistsException("Reel with this id is not exists");
        }
        return reelRepository.findById(id).map(this::mapToDTO).get();
    }
    @Override
    public ReelDTO mapToDTO(Reel entity) {
        return modelMapper.map(entity,ReelDTO.class);
    }
    @Override
    public Reel mapToEntity(ReelDTO dto) {
        return modelMapper.map(dto,Reel.class);
    }
}
