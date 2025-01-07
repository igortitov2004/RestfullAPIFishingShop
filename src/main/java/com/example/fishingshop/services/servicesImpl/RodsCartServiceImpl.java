package com.example.fishingshop.services.servicesImpl;

import com.example.fishingshop.DTOs.carts.rodsCart.RodCartEditAmountRequest;
import com.example.fishingshop.DTOs.carts.rodsCart.RodForCartResponse;
import com.example.fishingshop.DTOs.carts.rodsCart.RodsCartDTO;
import com.example.fishingshop.DTOs.carts.rodsCart.RodsCartCreationRequest;
import com.example.fishingshop.exceptions.rodsCartExceptions.RodsCartIsNotExistsException;
import com.example.fishingshop.interfaces.Map;
import com.example.fishingshop.models.RodsCart;
import com.example.fishingshop.repositories.RodsCartRepository;
import com.example.fishingshop.services.RodService;
import com.example.fishingshop.services.RodsCartService;
import com.example.fishingshop.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RodsCartServiceImpl implements RodsCartService, Map<RodsCartDTO, RodsCart> {
    private final RodsCartRepository rodsCartRepository;
    private final ModelMapper modelMapper;

    private final UserService userService;
    private final RodService rodService;

    @Override
    public List<RodForCartResponse> listByUserId(Long id) {
        if (!rodsCartRepository.existsRodsCartByUserId(id)) {
            throw new RodsCartIsNotExistsException("Rods cart with this user id is not exists");
        }
        return rodsCartRepository.findByUserId(id).stream().map(this::mapToResponse).toList();
    }

    @Override
    public void deleteById(Long id) {
        if (!rodsCartRepository.existsRodsCartById(id)) {
            throw new RodsCartIsNotExistsException("Rods cart with this id is not exists");
        }
        rodsCartRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteByUserId(Long id) {
        if (!rodsCartRepository.existsRodsCartByUserId(id)) {
            throw new RodsCartIsNotExistsException("Rods cart with this user id is not exists");
        }
        rodsCartRepository.deleteAllByUserId(id);
    }


    @Override
    public void add(RodsCartCreationRequest request, Long userId) {
        Optional<RodsCart> rodsCartOptional = rodsCartByUserIdAndRodId(userId, request.getRodId());
        if (rodsCartOptional.isPresent()) {
            addExistingRodsCart(rodsCartOptional);
        } else {
            addNewRodsCart(request, userId);
        }
    }


    private void addNewRodsCart(RodsCartCreationRequest request, Long userId) {
        RodsCartDTO rodsCartDTO = new RodsCartDTO();
        rodsCartDTO.setRod(rodService.getById(request.getRodId()));
        rodsCartDTO.setUser(userService.getById(userId));
        rodsCartDTO.setAmount(1);
        rodsCartRepository.save(mapToEntity(rodsCartDTO));
    }

    private void addExistingRodsCart(Optional<RodsCart> rodsCartOptional) {
        RodsCart rodsCart = rodsCartOptional.get();
        rodsCart.setAmount(rodsCart.getAmount() + 1);
        rodsCartRepository.save(rodsCart);
    }

    @Override
    public Optional<RodsCart> rodsCartByUserIdAndRodId(Long userId, Long rodId) {
        return rodsCartRepository.findRodsCartByUserIdAndRodId(userId, rodId);
    }

    @Override
    public void increaseAmount(RodCartEditAmountRequest request) {
        RodsCartDTO dto = getById(request.getId());
        dto.setAmount(dto.getAmount() + 1);
        rodsCartRepository.save(mapToEntity(dto));
    }

    @Override
    public void decreaseAmount(RodCartEditAmountRequest request) {
        RodsCartDTO dto = getById(request.getId());
        if(dto.getAmount()>1){
            dto.setAmount(dto.getAmount() - 1);
            rodsCartRepository.save(mapToEntity(dto));
        }
    }

    @Override
    public RodsCartDTO getById(Long id) {
        if (!rodsCartRepository.existsRodsCartById(id)) {
            throw new RodsCartIsNotExistsException("Rods cart with this id is not exists");
        }
        return rodsCartRepository.findById(id).map(this::mapToDTO).get();
    }

    @Override
    public RodsCartDTO mapToDTO(RodsCart entity) {
        return modelMapper.map(entity, RodsCartDTO.class);
    }

    @Override
    public RodsCart mapToEntity(RodsCartDTO dto) {
        return modelMapper.map(dto, RodsCart.class);
    }

    private RodForCartResponse mapToResponse(RodsCart entity) {
        return modelMapper.map(entity, RodForCartResponse.class);
    }
}
