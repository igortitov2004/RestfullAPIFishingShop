package com.example.fishingshop.services.servicesImpl;

import com.example.fishingshop.DTOs.RodsCartDTO;
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
public class RodsCartServiceImpl implements RodsCartService {

    private final RodsCartRepository rodsCartRepository;
    private final ModelMapper modelMapper;
    @Override
    public List<RodsCartDTO> getList(Long id) {
        return rodsCartRepository.findByUserId(id).stream().map(this::mapToDTO).toList();
    }


    private RodsCartDTO mapToDTO(RodsCart rodsCart){
        return modelMapper.map(rodsCart,RodsCartDTO.class);
    }
}
