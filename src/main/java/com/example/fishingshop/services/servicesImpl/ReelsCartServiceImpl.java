package com.example.fishingshop.services.servicesImpl;

import com.example.fishingshop.DTOs.reelsCart.ReelsCartDTO;
import com.example.fishingshop.DTOs.reelsCart.ReelsCartCreationRequest;
import com.example.fishingshop.DTOs.reelsCart.ReelsCartIncreaseAmountRequest;
import com.example.fishingshop.exceptions.reelsCartExceptions.ReelsCartIsNotExistsException;
import com.example.fishingshop.interfaces.Map;
import com.example.fishingshop.models.ReelsCart;
import com.example.fishingshop.repositories.ReelsCartRepository;
import com.example.fishingshop.services.ReelService;
import com.example.fishingshop.services.ReelsCartService;
import com.example.fishingshop.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ReelsCartServiceImpl implements Map<ReelsCartDTO, ReelsCart>,ReelsCartService {

    private final ModelMapper modelMapper;
    private final ReelsCartRepository reelsCartRepository;
    private final ReelService reelService;
    private final UserService userService;
    @Override
    public List<ReelsCartDTO> listByUserId(Long id) {
        if(!reelsCartRepository.existsReelsCartByUserId(id)){
            throw new ReelsCartIsNotExistsException("Reels cart with this user id is not exists");
        }
        return reelsCartRepository.findByUserId(id)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        if(!reelsCartRepository.existsReelsCartById(id)){
            throw new ReelsCartIsNotExistsException("Reels cart with this id is not exists");
        }
        reelsCartRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteByUserId(Long id) {
        if(!reelsCartRepository.existsReelsCartByUserId(id)){
            throw new ReelsCartIsNotExistsException("Reels cart with this user id is not exists");
        }
        reelsCartRepository.deleteAllByUserId(id);
    }

    @Override
    public void add(ReelsCartCreationRequest request){
        Optional<ReelsCart> reelsCartOptional = reelCartByUserIdAndReelId(request.getUserId(),request.getReelId());
        if(reelsCartOptional.isPresent()){
            ReelsCart reelsCart = reelsCartOptional.get();
            reelsCart.setAmount(reelsCart.getAmount()+1);
            reelsCartRepository.save(reelsCart);
        }else{
            ReelsCartDTO reelsCartDTO = new ReelsCartDTO();
            reelsCartDTO.setReel(reelService.getById(request.getReelId()));
            reelsCartDTO.setUser(userService.getById(request.getUserId()));
            reelsCartDTO.setAmount(1);
            reelsCartRepository.save(mapToEntity(reelsCartDTO));
        }
    }

    @Override
    public Optional<ReelsCart> reelCartByUserIdAndReelId(Long userId, Long reelId) {
        return reelsCartRepository.findReelsCartByUserIdAndReelId(userId,reelId);
    }

    @Override
    public void increaseAmount(ReelsCartIncreaseAmountRequest request){
       ReelsCartDTO dto = getById(request.getId());
       dto.setAmount(dto.getAmount()+1);
       reelsCartRepository.save(mapToEntity(dto));
    }

    @Override
    public ReelsCartDTO getById(Long id) {
        if(!reelsCartRepository.existsReelsCartById(id)){
            throw new ReelsCartIsNotExistsException("Reels cart with this id is not exists");
        }
        return reelsCartRepository.findById(id).map(this::mapToDTO).get();
    }




    @Override
    public ReelsCartDTO mapToDTO(ReelsCart entity) {
        return modelMapper.map(entity,ReelsCartDTO.class);
    }

    @Override
    public ReelsCart mapToEntity(ReelsCartDTO dto) {
        return modelMapper.map(dto, ReelsCart.class);
    }
}
