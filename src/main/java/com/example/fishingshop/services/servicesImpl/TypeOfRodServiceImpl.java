package com.example.fishingshop.services.servicesImpl;

import com.example.fishingshop.DTOs.typeOfReel.TypeOfReelDTO;
import com.example.fishingshop.DTOs.typeOfRod.TypeOfRodCreationRequest;
import com.example.fishingshop.DTOs.typeOfRod.TypeOfRodDTO;
import com.example.fishingshop.exceptions.typeOfRodExceptions.TypeOfRodAlreadyExistException;
import com.example.fishingshop.exceptions.typeOfRodExceptions.TypeOfRodIsNotExistException;
import com.example.fishingshop.interfaces.Map;
import com.example.fishingshop.models.TypeOfRod;
import com.example.fishingshop.repositories.TypeOfRodRepository;
import com.example.fishingshop.services.TypeOfRodService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TypeOfRodServiceImpl implements Map<TypeOfRodDTO, TypeOfRod>, TypeOfRodService {
    private final ModelMapper modelMapper;
    private final TypeOfRodRepository typeOfRodRepository;
    @Override
    public List<TypeOfRodDTO> list(String type) {
        if(type!=null){
            return typeOfRodRepository.findTypeOfRodByTypeContaining(type)
                    .stream()
                    .map(this::mapToDTO)
                    .toList();
        }
        return typeOfRodRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }
    @Override
    public void add(TypeOfRodCreationRequest request) {
         if(typeOfRodRepository.existsTypeOfRodByType(request.getType())){
             throw new TypeOfRodAlreadyExistException("Such a type of rod already exists");
         }
         typeOfRodRepository.save(modelMapper.map(request,TypeOfRod.class));
    }
    @Override
    public void delete(Long id) {
        if(!typeOfRodRepository.existsTypeOfRodById(id)){
            throw new TypeOfRodIsNotExistException("Type of rod with this id is not exist");
        }
        typeOfRodRepository.deleteById(id);
    }
    @Override
    public void edit(TypeOfRodDTO dto) {
        if(!typeOfRodRepository.existsTypeOfRodById(dto.getId())){
            throw new TypeOfRodIsNotExistException("Type of rod with this id is not exist");
        }
        Optional<TypeOfRod> typeOfRodOptional =
                Optional.ofNullable(typeOfRodRepository.findTypeOfRodByType(dto.getType()));
        if(typeOfRodOptional.isPresent()
                && !Objects.equals(typeOfRodOptional.get().getId(), dto.getId())){
            throw new TypeOfRodAlreadyExistException("Such a type of rod already exists");
        }
        typeOfRodRepository.save(mapToEntity(dto));
    }
    @Override
    public TypeOfRodDTO getById(Long id){
        if(!typeOfRodRepository.existsTypeOfRodById(id)){
            throw new TypeOfRodIsNotExistException("Type of rod with this id is not exist");
        }
        return typeOfRodRepository.findById(id).map(this::mapToDTO).get();
    }
    @Override
    public TypeOfRodDTO mapToDTO(TypeOfRod entity) {
        return modelMapper.map(entity,TypeOfRodDTO.class);
    }
    @Override
    public TypeOfRod mapToEntity(TypeOfRodDTO dto) {
        return modelMapper.map(dto,TypeOfRod.class);
    }
}
