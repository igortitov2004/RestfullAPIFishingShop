package com.example.fishingshop.services.servicesImpl;


import com.example.fishingshop.DTOs.reel.ReelCreationRequest;
import com.example.fishingshop.DTOs.reel.ReelDTO;
import com.example.fishingshop.DTOs.reel.ReelEditRequest;
import com.example.fishingshop.DTOs.reel.ReelsResponse;
import com.example.fishingshop.DTOs.rod.RodDTO;
import com.example.fishingshop.DTOs.rod.RodsResponse;
import com.example.fishingshop.exceptions.reelExceptions.ReelAlreadyExistsException;
import com.example.fishingshop.exceptions.reelExceptions.ReelIsNotExistsException;
import com.example.fishingshop.interfaces.Map;
import com.example.fishingshop.models.ImageReelsLink;
import com.example.fishingshop.models.ImageRodsLink;
import com.example.fishingshop.models.Reel;
import com.example.fishingshop.models.Rod;
import com.example.fishingshop.repositories.ImageReelLinkRepository;
import com.example.fishingshop.repositories.ImageRodsLinkRepository;
import com.example.fishingshop.repositories.ReelRepository;
import com.example.fishingshop.services.ManufacturerService;
import com.example.fishingshop.services.ReelService;
import com.example.fishingshop.services.TypeOfReelService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ReelServiceImpl implements Map<ReelDTO, Reel>, ReelService {
    private final ModelMapper modelMapper;
    private final ReelRepository reelRepository;
    private final ManufacturerService manufacturerService;
    private final TypeOfReelService typeOfReelService;

    private final ImageReelLinkRepository imageReelLinkRepository;

    @Override
    public List<ReelsResponse> getList(String name) {
        List<ReelsResponse> responseList = new ArrayList<>();
        List<Reel> reels;
        if(name!=null){
            reels = reelRepository.findReelByNameContaining(name);
        }else{
            reels = reelRepository.findAll();
        }
        for (ReelDTO dto:reels.stream()
                .map(this::mapToDTO)
                .toList()) {
            ReelsResponse response=modelMapper.map(dto,ReelsResponse.class);
            response.setLink(imageReelLinkRepository.findImageReelsLinkByReelId(response.getId()).getLink());
            responseList.add(response);
        }
        return responseList;
    }

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
    public void add(ReelCreationRequest request) {
        if(reelRepository.existsReelByNameAndPriceAndTypeIdAndManufacturerId(
                request.getName(),
                request.getPrice(),
                request.getTypeId(),
                request.getManufacturerId())){
            throw new ReelAlreadyExistsException("Such a reel is already exists");
        }
        ReelDTO reelDTO = ReelDTO.builder()
                .name(request.getName())
                .price(request.getPrice())
                .type(typeOfReelService.getById(request.getTypeId()))
                .manufacturer(manufacturerService.getById(request.getManufacturerId()))
                .build();
        Reel reel = reelRepository.save(mapToEntity(reelDTO));
        ImageReelsLink imageReelsLink = ImageReelsLink.builder()
                .link(request.getLink())
                .reel(reel)
                .build();
        imageReelLinkRepository.save(imageReelsLink);
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
        if(!reelRepository.existsReelById(request.getId())){
            throw new ReelIsNotExistsException("Reel with this id is not exists");
        }
        ReelDTO dto = getById(request.getId());
        Optional<Reel> reelOptional = reelRepository.findReelByNameAndPriceAndTypeIdAndManufacturerId(
                request.getName(),
                request.getPrice(),
                dto.getType().getId(),
                request.getManufacturerId());
        if(reelOptional.isPresent() && !Objects.equals(reelOptional.get().getId(), request.getId())){
            throw new ReelAlreadyExistsException("Such a reel is already exists");
        }else{
            dto.setName(request.getName());
            dto.setPrice(request.getPrice());
            dto.setManufacturer(manufacturerService.getById(request.getManufacturerId()));
        }
        reelRepository.save(mapToEntity(dto));
        if(request.getLink() != null){
            ImageReelsLink imageReelsLink = imageReelLinkRepository.findImageReelsLinkByReelId(request.getId());
            imageReelsLink.setLink(request.getLink());
            imageReelLinkRepository.save(imageReelsLink);
        }
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
