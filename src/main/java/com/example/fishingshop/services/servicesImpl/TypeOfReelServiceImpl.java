package com.example.fishingshop.services.servicesImpl;

import com.example.fishingshop.DTOs.typeOfReel.TypeOfReelCreationRequest;
import com.example.fishingshop.DTOs.typeOfReel.TypeOfReelDTO;
import com.example.fishingshop.exceptions.typeOfReelExceptions.TypeOfReelAlreadyExistsException;
import com.example.fishingshop.exceptions.typeOfReelExceptions.TypeOfReelIsNotExistsException;
import com.example.fishingshop.interfaces.Map;
import com.example.fishingshop.models.TypeOfReel;
import com.example.fishingshop.repositories.TypeOfReelRepository;
import com.example.fishingshop.services.TypeOfReelService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TypeOfReelServiceImpl implements Map<TypeOfReelDTO,TypeOfReel>, TypeOfReelService {

    private final ModelMapper modelMapper;
    private final TypeOfReelRepository typeOfReelRepository;

    @Override
    public List<TypeOfReelDTO> list(String type) {
        if(type!=null){
            return typeOfReelRepository.findTypeOfReelByTypeContaining(type)
                    .stream()
                    .map(this::mapToDTO)
                    .toList();
        }
        return typeOfReelRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public void add(TypeOfReelCreationRequest request) {
        if(typeOfReelRepository.existsTypeOfReelByType(request.getType())){
             throw new TypeOfReelAlreadyExistsException("Such a type of reel is already exists");
        }
        TypeOfReelDTO typeOfReelDTO = new TypeOfReelDTO();
        typeOfReelDTO.setType(request.getType());
        typeOfReelRepository.save(mapToEntity(typeOfReelDTO));
    }

    @Override
    public void delete(Long id) {
        if(!typeOfReelRepository.existsTypeOfReelById(id)){
             throw new TypeOfReelIsNotExistsException("Type of reel with this id is not exists");
        }
        typeOfReelRepository.deleteById(id);
    }

    @Override
    public void edit(TypeOfReelDTO dto) {
        typeOfReelRepository.save(mapToEntity(dto));
    }

    @Override
    public TypeOfReelDTO getById(Long id) {
        if(!typeOfReelRepository.existsTypeOfReelById(id)){
            throw new TypeOfReelIsNotExistsException("Type of reel with this id is not exists");
        }
        return typeOfReelRepository.findById(id).map(this::mapToDTO).get();
    }
    @Override
    public TypeOfReelDTO mapToDTO(TypeOfReel entity) {
        return modelMapper.map(entity,TypeOfReelDTO.class);
    }

    @Override
    public TypeOfReel mapToEntity(TypeOfReelDTO dto) {
        return modelMapper.map(dto,TypeOfReel.class);
    }


}
