package com.example.fishingshop.services.servicesImpl;

import com.example.fishingshop.DTOs.user.UserDTO;
import com.example.fishingshop.exceptions.userExceptions.UserIsNotExistsException;
import com.example.fishingshop.models.User;
import com.example.fishingshop.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userServiceImpl;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ModelMapper modelMapper;
    @Test
    void getById_whenExists() {
        Long id = 1L;
        User user = new User();
        UserDTO expected = new UserDTO();

        Mockito.when(userRepository.existsUserById(id)).thenReturn(true);
        Mockito.when(modelMapper.map(user,UserDTO.class)).thenReturn(expected);
        Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(user));

        UserDTO actual = userServiceImpl.getById(id);

        assertEquals(expected,actual);

        Mockito.verify(userRepository,Mockito.times(1)).findById(id);
    }
    @Test
    void getById_whenNotExists() {
        Long id = 1L;

        Mockito.when(userRepository.existsUserById(id)).thenReturn(false);

        assertThrows(UserIsNotExistsException.class,()->userServiceImpl.getById(id));

        Mockito.verify(userRepository,Mockito.times(0)).findById(id);
    }
}