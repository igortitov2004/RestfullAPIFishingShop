package com.example.fishingshop.services.servicesImpl;

import com.example.fishingshop.DTOs.UserDTO;
import com.example.fishingshop.exceptions.userExceptions.UserIsNotExistsException;
import com.example.fishingshop.interfaces.Map;
import com.example.fishingshop.models.User;
import com.example.fishingshop.repositories.UserRepository;
import com.example.fishingshop.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements Map<UserDTO, User>, UserService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    @Override
    public UserDTO mapToDTO(User entity) {
        return modelMapper.map(entity,UserDTO.class);
    }

    @Override
    public User mapToEntity(UserDTO dto) {
        return modelMapper.map(dto,User.class);
    }

    @Override
    public UserDTO getById(Long id) {
        if(!userRepository.existsUserById(id)){
            throw new UserIsNotExistsException("User with this id is not exists");
        }
        return userRepository.findById(id).map(this::mapToDTO).get();
    }
}
