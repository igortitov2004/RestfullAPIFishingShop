package com.example.fishingshop.services.servicesImpl;

import com.example.fishingshop.DTOs.RodsCartDTO;
import com.example.fishingshop.interfaces.Map;
import com.example.fishingshop.models.RodsCart;
import com.example.fishingshop.repositories.RodsCartRepository;
import com.example.fishingshop.services.RodsCartService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RodsCartServiceImpl implements RodsCartService, Map<RodsCartDTO,RodsCart> {
    private final RodsCartRepository rodsCartRepository;
    private final ModelMapper modelMapper;
    @Override
    public List<RodsCartDTO> getList(Long id){
        return rodsCartRepository.findByUserId(id).stream().map(this::mapToDTO).toList();
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
