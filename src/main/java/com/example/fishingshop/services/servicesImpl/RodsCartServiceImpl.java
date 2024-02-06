package com.example.fishingshop.services.servicesImpl;

import com.example.fishingshop.DTOs.rodsCart.RodsCartDTO;
import com.example.fishingshop.DTOs.rodsCart.RodsCartCreationRequest;
import com.example.fishingshop.exceptions.rodsCartExceptions.RodsCartIsNotExistsException;
import com.example.fishingshop.interfaces.Map;
import com.example.fishingshop.models.RodsCart;
import com.example.fishingshop.repositories.RodsCartRepository;
import com.example.fishingshop.services.RodService;
import com.example.fishingshop.services.RodsCartService;
import com.example.fishingshop.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RodsCartServiceImpl implements RodsCartService, Map<RodsCartDTO,RodsCart> {
    private final RodsCartRepository rodsCartRepository;
    private final ModelMapper modelMapper;

    private final UserService userService;
    private final RodService rodService;
    @Override
    public List<RodsCartDTO> listByUserId(Long id){
        if(!rodsCartRepository.existsRodsCartByUserId(id)){
            throw new RodsCartIsNotExistsException("Rods cart with this user id is not exists");
        }
        return rodsCartRepository.findByUserId(id).stream().map(this::mapToDTO).toList();
    }

    @Override
    public void deleteById(Long id){
        if(!rodsCartRepository.existsRodsCartById(id)){
            throw new RodsCartIsNotExistsException("Rods cart with this id is not exists");
        }
        rodsCartRepository.deleteById(id);
    }

    @Override
    public void deleteByUserId(Long id) {
        if(!rodsCartRepository.existsRodsCartByUserId(id)){
            throw new RodsCartIsNotExistsException("Rods cart with this user id is not exists");
        }
        rodsCartRepository.deleteRodsCartByUserId(id);
    }

    @Override
    public void add(RodsCartCreationRequest request) {
        RodsCartDTO rodsCartDTO = new RodsCartDTO();
        rodsCartDTO.setRod(rodService.getById(request.getRodId()));
        rodsCartDTO.setUser(userService.getById(request.getUserId()));
        rodsCartDTO.setAmount(request.getAmount());
        rodsCartRepository.save(mapToEntity(rodsCartDTO));
    }

    @Override
    public void edit(RodsCartDTO dto){
        if(!rodsCartRepository.existsRodsCartById(dto.getId())){
            throw new RodsCartIsNotExistsException("Rods cart with this id is not exists");
        }
        rodsCartRepository.save(mapToEntity(dto));
    }



    @Override
    public RodsCartDTO mapToDTO(RodsCart entity){
        return modelMapper.map(entity,RodsCartDTO.class);
    }
    @Override
    public RodsCart mapToEntity(RodsCartDTO dto){
        return modelMapper.map(dto,RodsCart.class);
    }
}
