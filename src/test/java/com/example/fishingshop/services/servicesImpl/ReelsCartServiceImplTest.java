package com.example.fishingshop.services.servicesImpl;

import com.example.fishingshop.DTOs.user.UserDTO;
import com.example.fishingshop.DTOs.reel.ReelDTO;
import com.example.fishingshop.DTOs.carts.reelsCart.ReelForCartResponse;
import com.example.fishingshop.DTOs.carts.reelsCart.ReelsCartCreationRequest;
import com.example.fishingshop.DTOs.carts.reelsCart.ReelsCartDTO;
import com.example.fishingshop.DTOs.carts.reelsCart.ReelsCartEditAmountRequest;
import com.example.fishingshop.exceptions.reelsCartExceptions.ReelsCartIsNotExistsException;
import com.example.fishingshop.models.Reel;
import com.example.fishingshop.models.ReelsCart;
import com.example.fishingshop.models.User;
import com.example.fishingshop.repositories.ReelsCartRepository;
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
class ReelsCartServiceImplTest {
    @InjectMocks
    private ReelsCartServiceImpl reelsCartServiceImpl;
    @Mock
    private ReelsCartRepository reelsCartRepository;
    @Mock
    private ReelServiceImpl reelServiceImpl;
    @Mock
    private UserServiceImpl userServiceImpl;
    @Mock
    private ModelMapper modelMapper;
    @Test
    void listByUserId_whenExists() {
        Mockito.when(reelsCartRepository.existsReelsCartByUserId(1L)).thenReturn(false);

        assertThrows(ReelsCartIsNotExistsException.class,()->reelsCartServiceImpl.listByUserId(1L));

        Mockito.verify(reelsCartRepository,Mockito.times(0)).findByUserId(1L);
    }
    @Test
    void listByUserId_whenNotExists() {
        List<ReelsCart> reelsCartList = new ArrayList<>(List.of(
                new ReelsCart(1L,null,null,1)
        ));
        ReelForCartResponse reelForCartResponse = new ReelForCartResponse();
        reelForCartResponse.setId(1L);
        reelForCartResponse.setReel(null);
        reelForCartResponse.setAmount(1);
        List<ReelForCartResponse> expected = new ArrayList<>(List.of(reelForCartResponse));

        Mockito.when(reelsCartRepository.existsReelsCartByUserId(2L)).thenReturn(true);
        Mockito.when(modelMapper.map(reelsCartList.get(0), ReelForCartResponse.class)).thenReturn(reelForCartResponse);
        Mockito.when(reelsCartRepository.findByUserId(2L)).thenReturn(reelsCartList);

        List<ReelForCartResponse> actual = reelsCartServiceImpl.listByUserId(2L);

        assertEquals(expected,actual);
    }
    @Test
    void deleteById_whenExists() {
        Mockito.when(reelsCartRepository.existsReelsCartById(1L)).thenReturn(true);

        reelsCartServiceImpl.deleteById(1L);

        Mockito.verify(reelsCartRepository,Mockito.times(1)).deleteById(1L);
    }
    @Test
    void deleteById_whenNotExists() {
        Mockito.when(reelsCartRepository.existsReelsCartById(1L)).thenReturn(false);

        assertThrows(ReelsCartIsNotExistsException.class, () -> reelsCartServiceImpl.deleteById(1L));

        Mockito.verify(reelsCartRepository,Mockito.times(0)).deleteById(1L);
    }
    @Test
    void deleteByUserId_whenExists() {
        Mockito.when(reelsCartRepository.existsReelsCartByUserId(1L)).thenReturn(true);

        reelsCartServiceImpl.deleteByUserId(1L);

        Mockito.verify(reelsCartRepository,Mockito.times(1)).deleteAllByUserId(1L);
    }
    @Test
    void deleteByUserId_whenNotExists(){
        Mockito.when(reelsCartRepository.existsReelsCartById(1L)).thenReturn(false);

        assertThrows(ReelsCartIsNotExistsException.class, () -> reelsCartServiceImpl.deleteByUserId(1L));

        Mockito.verify(reelsCartRepository,Mockito.times(0)).deleteAllByUserId(1L);
    }
    @Test
    void add_new(){
        User user = User.builder()
                .id(1L).build();
        ReelsCartCreationRequest request = new ReelsCartCreationRequest();
        request.setReelId(1L);


        ReelsCartDTO reelsCartDTO = new ReelsCartDTO();
        reelsCartDTO.setReel(new ReelDTO());
        reelsCartDTO.setUser(new UserDTO());
        reelsCartDTO.setAmount(1);

        ReelsCart reelsCart = new ReelsCart();
        reelsCart.setAmount(1);
        reelsCart.setUser(new User());
        reelsCart.setReel(new Reel());

        Mockito.when(reelsCartRepository.findReelsCartByUserIdAndReelId(user.getId(),request.getReelId())).thenReturn(Optional.empty());
        Mockito.when(reelServiceImpl.getById(request.getReelId())).thenReturn(new ReelDTO());
        Mockito.when(userServiceImpl.getById(user.getId())).thenReturn(new UserDTO());
        Mockito.when(modelMapper.map(reelsCartDTO,ReelsCart.class)).thenReturn(reelsCart);

        reelsCartServiceImpl.add(request,user.getId());

        Mockito.verify(reelsCartRepository,Mockito.times(1)).save(reelsCart);
    }
    @Test
    void add_existing(){
        User user = User.builder()
                .id(1L).build();
         ReelsCartCreationRequest request = new ReelsCartCreationRequest();
         request.setReelId(1L);

         Optional<ReelsCart> reelsCartOptional = Optional.of(new ReelsCart());
         reelsCartOptional.get().setAmount(1);

         ReelsCart reelsCart = new ReelsCart();
         reelsCart.setAmount(2);

         Mockito.when(reelsCartRepository.findReelsCartByUserIdAndReelId(user.getId(),request.getReelId())).thenReturn(reelsCartOptional);

         reelsCartServiceImpl.add(request,user.getId());

         Mockito.verify(reelsCartRepository,Mockito.times(1)).save(reelsCart);
    }

    @Test
    void increaseAmount_whenExists(){
        ReelsCartEditAmountRequest request = new ReelsCartEditAmountRequest();
        request.setId(100L);

        ReelsCartDTO reelsCartDTO = new ReelsCartDTO();
        reelsCartDTO.setId(100L);
        reelsCartDTO.setAmount(1);

        ReelsCart reelsCart = new ReelsCart();
        reelsCart.setId(100L);
        reelsCart.setAmount(1);

        Mockito.when(reelsCartRepository.existsReelsCartById(reelsCart.getId())).thenReturn(true);
        Mockito.when(reelsCartRepository.existsReelsCartById(request.getId())).thenReturn(true);
        Mockito.when(reelsCartRepository.findById(request.getId())).thenReturn(Optional.of(reelsCart));
        Mockito.when(modelMapper.map(reelsCart,ReelsCartDTO.class)).thenReturn(reelsCartDTO);
        Mockito.when(modelMapper.map(reelsCartDTO,ReelsCart.class)).thenReturn(reelsCart);

        reelsCartServiceImpl.increaseAmount(request);

        Mockito.verify(reelsCartRepository,Mockito.times(1)).save(reelsCart);
    }
    @Test
    void increaseAmount_whenNotExists(){
        ReelsCartEditAmountRequest request = new ReelsCartEditAmountRequest();
        request.setId(100L);

        Mockito.when(reelsCartRepository.existsReelsCartById(request.getId())).thenReturn(false);

        assertThrows(ReelsCartIsNotExistsException.class, () -> reelsCartServiceImpl.getById(request.getId()));

        Mockito.verify(reelsCartRepository,Mockito.times(0)).findById(request.getId());
    }
    @Test
    void getById_whenExists() {
        ReelsCartDTO dto = new ReelsCartDTO();
        dto.setId(2L);
        ReelsCart reelsCart = new ReelsCart();
        reelsCart.setId(2L);

        Mockito.when(modelMapper.map(reelsCart,ReelsCartDTO.class)).thenReturn(dto);
        Mockito.when(reelsCartRepository.existsReelsCartById(2L)).thenReturn(true);
        Mockito.when(reelsCartRepository.findById(2L)).thenReturn(Optional.of(reelsCart));

        assertEquals(dto, reelsCartServiceImpl.getById(2L));
    }
    @Test
    void getById_whenNotExists() {
        ReelsCart reelsCart = new ReelsCart();
        reelsCart.setId(100L);

        Mockito.when(reelsCartRepository.existsReelsCartById(reelsCart.getId())).thenReturn(false);

        assertThrows(ReelsCartIsNotExistsException.class, () -> reelsCartServiceImpl.getById(reelsCart.getId()));

        Mockito.verify(reelsCartRepository,Mockito.times(0)).findById(reelsCart.getId());
    }
}