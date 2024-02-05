package com.example.fishingshop.services.servicesImpl;


import com.example.fishingshop.DTOs.ReelDTO;
import com.example.fishingshop.DTOs.RodDTO;
import com.example.fishingshop.exceptions.reelExceptions.ReelIsNotExistsException;
import com.example.fishingshop.interfaces.Map;
import com.example.fishingshop.models.Reel;
import com.example.fishingshop.repositories.ReelRepository;
import com.example.fishingshop.services.ReelService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReelServiceImpl implements Map<ReelDTO, Reel>, ReelService {

    private final ModelMapper modelMapper;

    private final ReelRepository reelRepository;

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
    public void add(ReelDTO dto) {
        // Valid
        reelRepository.save(mapToEntity(dto));
    }

    @Override
    public void delete(Long id) {
        if(!reelRepository.existsReelById(id)){
            throw new ReelIsNotExistsException("Reel with this id is not exists");
        }
        reelRepository.deleteById(id);
    }

    @Override
    public void edit(ReelDTO dto) {
        if(!reelRepository.existsReelById(dto.getId())){
            throw new ReelIsNotExistsException("Reel with this id is not exists");
        }
        // Valid
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
