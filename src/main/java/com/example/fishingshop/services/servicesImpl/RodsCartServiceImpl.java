package com.example.fishingshop.services.servicesImpl;

import com.example.fishingshop.DTOs.RodsCartDTO;
import com.example.fishingshop.exceptions.rodsCartExceptions.RodsCartIsNotExistsException;
import com.example.fishingshop.interfaces.Map;
import com.example.fishingshop.models.RodsCart;
import com.example.fishingshop.repositories.RodsCartRepository;
import com.example.fishingshop.services.RodsCartService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RodsCartServiceImpl implements RodsCartService, Map<RodsCartDTO,RodsCart> {
    private final RodsCartRepository rodsCartRepository;
    private final ModelMapper modelMapper;
    @Override
    public List<RodsCartDTO> listByUserId(Long id){
        if(!rodsCartRepository.existsRodsCartByUserId(id)){
            throw new RodsCartIsNotExistsException("Rods cart with this user id is not exists");
        }
        return rodsCartRepository.findByUserId(id).stream().map(this::mapToDTO).toList();
    }

    @Override
    public void delete(Long id){
        if(!rodsCartRepository.existsRodsCartById(id)){
            throw new RodsCartIsNotExistsException("Rods cart with this id is not exists");
        }
        rodsCartRepository.deleteById(id);
    }

    @Override
    public void add(RodsCartDTO dto) {
        rodsCartRepository.save(mapToEntity(dto));
    }

    @Override
    public void edit(RodsCartDTO dto){
        if(!rodsCartRepository.existsRodsCartById(dto.getId())){
            throw new RodsCartIsNotExistsException("Rods cart with this id is not exists");
        }
        rodsCartRepository.save(mapToEntity(dto));
    }

    @Override
    public RodsCartDTO getById(Long id){
        if(!rodsCartRepository.existsRodsCartById(id)){
            throw new RodsCartIsNotExistsException("Rods cart with this id is not exists");
        }
        return rodsCartRepository.findById(id).map(this::mapToDTO).get();
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
