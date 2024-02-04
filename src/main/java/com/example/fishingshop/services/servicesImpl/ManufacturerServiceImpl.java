package com.example.fishingshop.services.servicesImpl;

import com.example.fishingshop.DTOs.ManufacturerDTO;
import com.example.fishingshop.exceptions.manufacturerExceptions.ManufacturerAlreadyExistException;
import com.example.fishingshop.exceptions.manufacturerExceptions.ManufacturerIsNotExist;
import com.example.fishingshop.interfaces.Map;
import com.example.fishingshop.models.Manufacturer;
import com.example.fishingshop.repositories.ManufacturerRepository;
import com.example.fishingshop.services.ManufacturerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ManufacturerServiceImpl implements
        Map<ManufacturerDTO, Manufacturer>, ManufacturerService {
    private final ModelMapper modelMapper;
    private final ManufacturerRepository manufacturerRepository;
    @Override
    public List<ManufacturerDTO> list(String name) {
        if(name!=null){
            return manufacturerRepository
                    .findManufacturerByNameContaining(name)
                    .stream()
                    .map(this::mapToDTO)
                    .toList();
        }
        return manufacturerRepository.findAll().stream().map(this::mapToDTO).toList();
    }
    @Override
    public void add(ManufacturerDTO dto) {
        if(manufacturerRepository.existsManufacturersByName(dto.getName())){
            throw new ManufacturerAlreadyExistException("Such a manufacturer already exists");
        }
        manufacturerRepository.save(mapToEntity(dto));
    }
    @Override
    public void delete(Long id){
        if(!manufacturerRepository.existsManufacturersById(id)){
            throw new ManufacturerIsNotExist("Manufacturer with this id is not exist");
        }
        manufacturerRepository.deleteById(id);
    }
    @Override
    public void edit(ManufacturerDTO dto){
        if(!manufacturerRepository.existsManufacturersById(dto.getId())){
            throw new ManufacturerIsNotExist("Manufacturer with this id is not exist");
        }
        Optional<Manufacturer> manufacturerOptional =
                Optional.ofNullable(manufacturerRepository.findManufacturerByName(dto.getName()));
        if(manufacturerOptional.isPresent()
                && !Objects.equals(manufacturerOptional.get().getId(), dto.getId())){
            throw new ManufacturerAlreadyExistException("Such a manufacturer already exists");
        }
        manufacturerRepository.save(mapToEntity(dto));
    }

    @Override
    public ManufacturerDTO getById(Long id) {
        if(!manufacturerRepository.existsManufacturersById(id)){
            throw new ManufacturerIsNotExist("Manufacturer with this id is not exist");
        }
        return manufacturerRepository.findById(id).map(this::mapToDTO).get();
    }

    @Override
    public ManufacturerDTO mapToDTO(Manufacturer entity) {
        return modelMapper.map(entity,ManufacturerDTO.class);
    }
    @Override
    public Manufacturer mapToEntity(ManufacturerDTO dto) {
        return modelMapper.map(dto,Manufacturer.class);
    }
}
