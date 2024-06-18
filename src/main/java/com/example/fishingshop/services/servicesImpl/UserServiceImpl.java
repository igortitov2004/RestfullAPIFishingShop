package com.example.fishingshop.services.servicesImpl;

import com.example.fishingshop.DTOs.user.ChangePasswordRequest;
import com.example.fishingshop.DTOs.user.UserDTO;
import com.example.fishingshop.exceptions.userExceptions.UserAlreadyExistsException;
import com.example.fishingshop.exceptions.userExceptions.UserIsNotExistsException;
import com.example.fishingshop.interfaces.Map;
import com.example.fishingshop.models.User;
import com.example.fishingshop.repositories.UserRepository;
import com.example.fishingshop.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements Map<UserDTO, User>, UserService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }
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


    @Override
    public void create(User user) {
        if(userRepository.existsUserByEmail(user.getEmail())){
            throw new UserAlreadyExistsException("User with this email already exists");
        }
        userRepository.save(user);
    }
}
