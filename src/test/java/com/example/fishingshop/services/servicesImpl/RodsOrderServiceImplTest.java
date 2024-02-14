package com.example.fishingshop.services.servicesImpl;

import com.example.fishingshop.DTOs.orders.ReelsForOrderResponse;
import com.example.fishingshop.DTOs.orders.ReelsOrderDTO;
import com.example.fishingshop.DTOs.orders.RodsForOrderResponse;
import com.example.fishingshop.DTOs.orders.RodsOrderDTO;
import com.example.fishingshop.DTOs.reel.ReelDTO;
import com.example.fishingshop.DTOs.reelsCart.ReelCartResponse;
import com.example.fishingshop.DTOs.rod.RodDTO;
import com.example.fishingshop.DTOs.rodsCart.RodCartResponse;
import com.example.fishingshop.models.*;
import com.example.fishingshop.repositories.ReelsOrderRepository;
import com.example.fishingshop.repositories.RodsOrderRepository;
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
class RodsOrderServiceImplTest {

    @InjectMocks
    private RodsOrderServiceImpl reelsOrderServiceImpl;
    @Mock
    private RodsOrderRepository reelsOrderRepository;
    @Mock
    private RodsCartServiceImpl reelsCartServiceImpl;
    @Mock
    private ModelMapper modelMapper;
    @Test
    void listByOrderId() {
        List<RodsForOrderResponse> expected = new ArrayList<>();
        expected.add(RodsForOrderResponse.builder()
                .rodDTO(new RodDTO())
                .amount(1)
                .build());
        RodsOrder rodsOrder = RodsOrder.builder()
                .rod(new Rod())
                .amount(1)
                .build();

        Mockito.when(modelMapper.map(rodsOrder,RodsForOrderResponse.class)).thenReturn(expected.get(0));
        Mockito.when(reelsOrderRepository.findRodsOrderByOrderId(1L)).thenReturn(List.of(rodsOrder));

        List<RodsForOrderResponse> actual = reelsOrderServiceImpl.listByOrderId(1L);

        assertEquals(expected,actual);
    }
    @Test
    void add(){
        Order order = Order.builder()
                .address("address")
                .user(User.builder()
                        .id(1L)
                        .build())
                .build();
        RodCartResponse response = RodCartResponse.builder()
                .rod(new RodDTO())
                .amount(1)
                .build();
        RodsOrderDTO dto = RodsOrderDTO.builder()
                .rod(response.getRod())
                .amount(response.getAmount())
                .build();
        RodsOrder rodsOrder = RodsOrder.builder()
                .order(order)
                .rod(new Rod())
                .amount(1)
                .build();

        Mockito.when(modelMapper.map(dto, RodsOrder.class)).thenReturn(rodsOrder);
        Mockito.when(reelsCartServiceImpl.listByUserId(order.getUser().getId())).thenReturn(List.of(response));

        reelsOrderServiceImpl.add(order);

        Mockito.verify(reelsOrderRepository,Mockito.times(1)).save(rodsOrder);
        Mockito.verify(reelsCartServiceImpl,Mockito.times(1)).deleteByUserId(order.getUser().getId());
    }
}