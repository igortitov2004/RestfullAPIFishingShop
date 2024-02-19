package com.example.fishingshop.services.servicesImpl;

import com.example.fishingshop.DTOs.user.UserDTO;
import com.example.fishingshop.DTOs.rod.RodDTO;
import com.example.fishingshop.DTOs.rodsCart.RodCartIncreaseAmountRequest;
import com.example.fishingshop.DTOs.rodsCart.RodCartResponse;
import com.example.fishingshop.DTOs.rodsCart.RodsCartCreationRequest;
import com.example.fishingshop.DTOs.rodsCart.RodsCartDTO;
import com.example.fishingshop.exceptions.rodsCartExceptions.RodsCartIsNotExistsException;
import com.example.fishingshop.models.*;
import com.example.fishingshop.repositories.RodsCartRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class RodsCartServiceImplTest {

    @InjectMocks
    private RodsCartServiceImpl rodsCartServiceImpl;
    @Mock
    private RodsCartRepository rodsCartRepository;
    @Mock
    private RodServiceImpl rodServiceImpl;
    @Mock
    private UserServiceImpl userServiceImpl;
    @Mock
    private ModelMapper modelMapper;
    @Test
    void listByUserId_whenExists() {
        Mockito.when(rodsCartRepository.existsRodsCartByUserId(1L)).thenReturn(false);

        assertThrows(RodsCartIsNotExistsException.class,()-> rodsCartServiceImpl.listByUserId(1L));

        Mockito.verify(rodsCartRepository,Mockito.times(0)).findByUserId(1L);
    }
    @Test
    void listByUserId_whenNotExists() {
        List<RodsCart> rodsCartList = new ArrayList<>(List.of(
                new RodsCart(1L,null,null,1)
        ));
        RodCartResponse rodCartResponse = new RodCartResponse();
        rodCartResponse.setId(1L);
        rodCartResponse.setRod(null);
        rodCartResponse.setAmount(1);
        List<RodCartResponse> expected = new ArrayList<>(List.of(rodCartResponse));

        Mockito.when(rodsCartRepository.existsRodsCartByUserId(2L)).thenReturn(true);
        Mockito.when(modelMapper.map(rodsCartList.get(0),RodCartResponse.class)).thenReturn(rodCartResponse);
        Mockito.when(rodsCartRepository.findByUserId(2L)).thenReturn(rodsCartList);

        List<RodCartResponse> actual = rodsCartServiceImpl.listByUserId(2L);

        assertEquals(expected,actual);
    }
    @Test
    void deleteById_whenExists() {
        Mockito.when(rodsCartRepository.existsRodsCartById(1L)).thenReturn(true);

        rodsCartServiceImpl.deleteById(1L);

        Mockito.verify(rodsCartRepository,Mockito.times(1)).deleteById(1L);
    }
    @Test
    void deleteById_whenNotExists() {
        Mockito.when(rodsCartRepository.existsRodsCartById(1L)).thenReturn(false);

        assertThrows(RodsCartIsNotExistsException.class, () -> rodsCartServiceImpl.deleteById(1L));

        Mockito.verify(rodsCartRepository,Mockito.times(0)).deleteById(1L);
    }
    @Test
    void deleteByUserId_whenExists() {
        Mockito.when(rodsCartRepository.existsRodsCartByUserId(1L)).thenReturn(true);

        rodsCartServiceImpl.deleteByUserId(1L);

        Mockito.verify(rodsCartRepository,Mockito.times(1)).deleteAllByUserId(1L);
    }
    @Test
    void deleteByUserId_whenNotExists(){
        Mockito.when(rodsCartRepository.existsRodsCartByUserId(1L)).thenReturn(false);

        assertThrows(RodsCartIsNotExistsException.class, () -> rodsCartServiceImpl.deleteByUserId(1L));

        Mockito.verify(rodsCartRepository,Mockito.times(0)).deleteAllByUserId(1L);
    }
    @Test
    void add_new(){
        RodsCartCreationRequest request = new RodsCartCreationRequest();
        request.setRodId(1L);
        request.setUserId(1L);

        RodsCartDTO rodsCartDTO = new RodsCartDTO();
        rodsCartDTO.setRod(new RodDTO());
        rodsCartDTO.setUser(new UserDTO());
        rodsCartDTO.setAmount(1);

        RodsCart rodsCart = new RodsCart();
        rodsCart.setAmount(1);
        rodsCart.setUser(new User());
        rodsCart.setRod(new Rod());

        Mockito.when(rodsCartRepository.findRodsCartByUserIdAndRodId(request.getUserId(),request.getRodId())).thenReturn(Optional.empty());
        Mockito.when(rodServiceImpl.getById(request.getRodId())).thenReturn(new RodDTO());
        Mockito.when(userServiceImpl.getById(request.getUserId())).thenReturn(new UserDTO());
        Mockito.when(modelMapper.map(rodsCartDTO,RodsCart.class)).thenReturn(rodsCart);

        rodsCartServiceImpl.add(request);

        Mockito.verify(rodsCartRepository,Mockito.times(1)).save(rodsCart);
    }
    @Test
    void add_existing(){
        RodsCartCreationRequest request = new RodsCartCreationRequest();
        request.setUserId(1L);
        request.setRodId(1L);

        Optional<RodsCart> rodsCartOptional = Optional.of(new RodsCart());
        rodsCartOptional.get().setAmount(1);

        RodsCart rodsCart = new RodsCart();
        rodsCart.setAmount(2);

        Mockito.when(rodsCartRepository.findRodsCartByUserIdAndRodId(request.getUserId(),request.getRodId())).thenReturn(rodsCartOptional);

        rodsCartServiceImpl.add(request);

        Mockito.verify(rodsCartRepository,Mockito.times(1)).save(rodsCart);
    }

    @Test
    void increaseAmount_whenExists(){
        RodCartIncreaseAmountRequest request = new RodCartIncreaseAmountRequest();
        request.setId(100L);

        RodsCartDTO rodsCartDTO = new RodsCartDTO();
        rodsCartDTO.setId(100L);
        rodsCartDTO.setAmount(1);

        RodsCart rodsCart = new RodsCart();
        rodsCart.setId(100L);
        rodsCart.setAmount(1);

        Mockito.when(rodsCartRepository.existsRodsCartById(rodsCart.getId())).thenReturn(true);
        Mockito.when(rodsCartRepository.existsRodsCartById(request.getId())).thenReturn(true);
        Mockito.when(rodsCartRepository.findById(request.getId())).thenReturn(Optional.of(rodsCart));
        Mockito.when(modelMapper.map(rodsCart,RodsCartDTO.class)).thenReturn(rodsCartDTO);
        Mockito.when(modelMapper.map(rodsCartDTO,RodsCart.class)).thenReturn(rodsCart);

        rodsCartServiceImpl.increaseAmount(request);

        Mockito.verify(rodsCartRepository,Mockito.times(1)).save(rodsCart);
    }
    @Test
    void increaseAmount_whenNotExists(){
        RodCartIncreaseAmountRequest request = new RodCartIncreaseAmountRequest();
        request.setId(100L);

        Mockito.when(rodsCartRepository.existsRodsCartById(request.getId())).thenReturn(false);

        assertThrows(RodsCartIsNotExistsException.class, () -> rodsCartServiceImpl.getById(request.getId()));

        Mockito.verify(rodsCartRepository,Mockito.times(0)).findById(request.getId());
    }
    @Test
    void getById_whenExists() {
        RodsCartDTO dto = new RodsCartDTO();
        dto.setId(2L);
        RodsCart rodsCart = new RodsCart();
        rodsCart.setId(2L);

        Mockito.when(modelMapper.map(rodsCart,RodsCartDTO.class)).thenReturn(dto);
        Mockito.when(rodsCartRepository.existsRodsCartById(2L)).thenReturn(true);
        Mockito.when(rodsCartRepository.findById(2L)).thenReturn(Optional.of(rodsCart));

        assertEquals(dto, rodsCartServiceImpl.getById(2L));
    }
    @Test
    void getById_whenNotExists() {
        RodsCart rodsCart = new RodsCart();
        rodsCart.setId(100L);

        Mockito.when(rodsCartRepository.existsRodsCartById(rodsCart.getId())).thenReturn(false);

        assertThrows(RodsCartIsNotExistsException.class, () -> rodsCartServiceImpl.getById(rodsCart.getId()));

        Mockito.verify(rodsCartRepository,Mockito.times(0)).findById(rodsCart.getId());
    }
}