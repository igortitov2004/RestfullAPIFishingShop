package com.example.fishingshop.services.servicesImpl;

import com.example.fishingshop.DTOs.reel.ReelDTO;
import com.example.fishingshop.DTOs.rod.RodCreationRequest;
import com.example.fishingshop.DTOs.rod.RodDTO;
import com.example.fishingshop.DTOs.rod.RodEditRequest;
import com.example.fishingshop.DTOs.rod.RodsResponse;
import com.example.fishingshop.exceptions.reelExceptions.ReelAlreadyExistsException;
import com.example.fishingshop.exceptions.reelExceptions.ReelIsNotExistsException;
import com.example.fishingshop.exceptions.rodExceptions.RodAlreadyExistsException;
import com.example.fishingshop.exceptions.rodExceptions.RodIsNotExistException;
import com.example.fishingshop.interfaces.Map;
import com.example.fishingshop.models.ImageReelsLink;
import com.example.fishingshop.models.ImageRodsLink;
import com.example.fishingshop.models.Reel;
import com.example.fishingshop.models.Rod;
import com.example.fishingshop.repositories.ImageRodsLinkRepository;
import com.example.fishingshop.repositories.RodRepository;
import com.example.fishingshop.services.ManufacturerService;
import com.example.fishingshop.services.RodService;
import com.example.fishingshop.services.TypeOfRodService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RodServiceImpl implements Map<RodDTO, Rod>, RodService {
    private final ModelMapper modelMapper;
    private final RodRepository rodRepository;
    private final ManufacturerService manufacturerService;
    private final TypeOfRodService typeOfRodService;
    private final ImageRodsLinkRepository imageRodsLinkRepository;

    @Override
    public List<RodsResponse> getList(String name){
        List<RodsResponse> responseList = new ArrayList<>();
        List<Rod> rods;
        if(name!=null){
             rods = rodRepository.findRodByNameContaining(name);
        }else{
             rods = rodRepository.findAll();
        }
        for (RodDTO dto:rods.stream()
                .map(this::mapToDTO)
                .toList()) {
            RodsResponse response=modelMapper.map(dto,RodsResponse.class);
            ImageRodsLink imageRodsLink = imageRodsLinkRepository.findImageRodsLinkByRodId(response.getId());
            if(imageRodsLink!=null) {
                response.setLink(imageRodsLink.getLink());
            }
            responseList.add(response);
        }
        return responseList;

    }
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
    public void add(RodCreationRequest request) {
        if(rodRepository.existsRodByNameAndLengthAndWeightAndTestLoadAndPriceAndTypeIdAndManufacturerId(
                request.getName(),
                request.getLength(),
                request.getWeight(),
                request.getTestLoad(),
                request.getPrice(),
                request.getTypeId(),
                request.getManufacturerId()
        )){
            throw new RodAlreadyExistsException("Such a rod is already exists");
        }
        RodDTO rodDTO = RodDTO.builder()
                .name(request.getName())
                .price(request.getPrice())
                .length(request.getLength())
                .testLoad(request.getTestLoad())
                .weight(request.getWeight())
                .manufacturer(manufacturerService.getById(request.getManufacturerId()))
                .type(typeOfRodService.getById(request.getTypeId()))
                .build();
        Rod rod=rodRepository.save(mapToEntity(rodDTO));
        ImageRodsLink imageRodsLink = ImageRodsLink.builder()
                .link(request.getLink())
                .rod(rod)
                .build();
        imageRodsLinkRepository.save(imageRodsLink);
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
        if(!rodRepository.existsRodById(request.getId())){
            throw new RodIsNotExistException("Rod with this id is not exists");
        }
        RodDTO dto = getById(request.getId());
        Optional<Rod> rodOptional = rodRepository.findRodByNameAndLengthAndWeightAndTestLoadAndPriceAndTypeIdAndManufacturerId(
                request.getName(),
                dto.getLength(),
                dto.getWeight(),
                dto.getTestLoad(),
                request.getPrice(),
                dto.getType().getId(),
                request.getManufacturerId());
        if(rodOptional.isPresent() && !Objects.equals(rodOptional.get().getId(), request.getId())){
            throw new RodAlreadyExistsException("Such a reel is already exists");
        }else{
            dto.setName(request.getName());
            dto.setPrice(request.getPrice());
            dto.setManufacturer(manufacturerService.getById(request.getManufacturerId()));
        }
        rodRepository.save(mapToEntity(dto));
        if(request.getLink() != null){
            ImageRodsLink imageRodsLink = imageRodsLinkRepository.findImageRodsLinkByRodId(request.getId());
            imageRodsLink.setLink(request.getLink());
            imageRodsLinkRepository.save(imageRodsLink);
        }
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
