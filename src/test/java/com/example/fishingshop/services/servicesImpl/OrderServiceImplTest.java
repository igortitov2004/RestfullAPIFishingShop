package com.example.fishingshop.services.servicesImpl;

import com.example.fishingshop.DTOs.user.UserDTO;
import com.example.fishingshop.DTOs.orders.OrderDTO;
import com.example.fishingshop.DTOs.orders.OrderResponse;
import com.example.fishingshop.exceptions.orderExceptions.OrderIsNotExistsException;
import com.example.fishingshop.models.Order;
import com.example.fishingshop.models.User;
import com.example.fishingshop.repositories.OrderRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderServiceImpl;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private ReelsOrderServiceImpl reelsOrderServiceImpl;
    @Mock
    private RodsOrderServiceImpl rodsOrderServiceImpl;
    @Mock
    private UserServiceImpl userServiceImpl;

    @Test
    void add(){
        String address="address";
        User user = User.builder()
                .id(2L)
                .build();
        UserDTO userDTO = UserDTO.builder()
                .id(2L)
                .build();
        Order order = Order.builder()
                .address(address)
                .user(user)
                .build();

        Mockito.when(modelMapper.map(userDTO,User.class)).thenReturn(user);
        Mockito.when(userServiceImpl.getById(2L)).thenReturn(userDTO);
        Mockito.when(orderRepository.save(order)).thenReturn(order);

        Order actual = orderServiceImpl.add(address);

        assertNotNull(actual);

        Mockito.verify(reelsOrderServiceImpl,Mockito.times(1)).add(order);
        Mockito.verify(rodsOrderServiceImpl,Mockito.times(1)).add(order);
    }

    @Test
    void list_whenNotExists(){
        Long userId = 1L;
        Mockito.when(orderRepository.existsOrderByUserId(userId)).thenReturn(false);

        assertThrows(OrderIsNotExistsException.class,()->orderServiceImpl.list(userId));

        Mockito.verify(orderRepository,Mockito.times(0)).findOrderByUserId(userId);
    }

    @Test
    void list_whenExists(){
        Long userId = 1L;

        OrderDTO dto = OrderDTO.builder()
                .id(1L)
                .build();

        Order order = Order.builder()
                .id(1L)
                .build();

        List<OrderResponse> expected = new ArrayList<>();
        expected.add(OrderResponse.builder()
                        .reelsForOrderResponseList(new ArrayList<>())
                        .rodsForOrderResponseList(new ArrayList<>())
                        .build());

        Mockito.when(modelMapper.map(order,OrderDTO.class)).thenReturn(dto);
        Mockito.when(orderRepository.existsOrderByUserId(userId)).thenReturn(true);
        Mockito.when(orderRepository.findOrderByUserId(userId)).thenReturn(List.of(order));
        Mockito.when(reelsOrderServiceImpl.listByOrderId(dto.getId())).thenReturn(new ArrayList<>());
        Mockito.when(rodsOrderServiceImpl.listByOrderId(dto.getId())).thenReturn(new ArrayList<>());

        List<OrderResponse> actual = orderServiceImpl.list(userId);

        assertEquals(expected,actual);
    }


    @Test
    void deleteById_whenExists() {
        Mockito.when(orderRepository.existsOrderById(1L)).thenReturn(true);

        orderServiceImpl.deleteById(1L);

        Mockito.verify(orderRepository,Mockito.times(1)).deleteById(1L);
    }
    @Test
    void deleteById_whenNotExists() {
        Mockito.when(orderRepository.existsOrderById(1L)).thenReturn(false);

        assertThrows(OrderIsNotExistsException.class,()->orderServiceImpl.deleteById(1L));

        Mockito.verify(orderRepository,Mockito.times(0)).deleteById(1L);
    }
}