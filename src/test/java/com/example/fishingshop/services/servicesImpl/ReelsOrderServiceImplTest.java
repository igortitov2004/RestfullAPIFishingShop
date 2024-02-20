package com.example.fishingshop.services.servicesImpl;

import com.example.fishingshop.DTOs.orders.ReelsForOrderResponse;
import com.example.fishingshop.DTOs.orders.ReelsOrderDTO;
import com.example.fishingshop.DTOs.reel.ReelDTO;
import com.example.fishingshop.DTOs.carts.reelsCart.ReelForCartResponse;
import com.example.fishingshop.models.Order;
import com.example.fishingshop.models.Reel;
import com.example.fishingshop.models.ReelsOrder;
import com.example.fishingshop.models.User;
import com.example.fishingshop.repositories.ReelsOrderRepository;
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
class ReelsOrderServiceImplTest {
    @InjectMocks
    private ReelsOrderServiceImpl reelsOrderServiceImpl;
    @Mock
    private ReelsOrderRepository reelsOrderRepository;
    @Mock
    private ReelsCartServiceImpl reelsCartServiceImpl;
    @Mock
    private ModelMapper modelMapper;
    @Test
    void listByOrderId() {
        List<ReelsForOrderResponse> expected = new ArrayList<>();
        expected.add(ReelsForOrderResponse.builder()
                .reel(new ReelDTO())
                .amount(1)
                .build());
        ReelsOrder reelsOrder = ReelsOrder.builder()
                .reel(new Reel())
                .amount(1)
                .build();

        Mockito.when(modelMapper.map(reelsOrder,ReelsForOrderResponse.class)).thenReturn(expected.get(0));
        Mockito.when(reelsOrderRepository.findReelsOrderByOrderId(1L)).thenReturn(List.of(reelsOrder));

        List<ReelsForOrderResponse> actual = reelsOrderServiceImpl.listByOrderId(1L);

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
        ReelDTO reelDTO = ReelDTO.builder()
                .price(1d)
                .build();
        ReelForCartResponse response = ReelForCartResponse.builder()
                .reel(reelDTO)
                .amount(3)
                .build();
        ReelsOrderDTO dto = ReelsOrderDTO.builder()
                .reel(response.getReel())
                .amount(response.getAmount())
                .build();
        ReelsOrder reelsOrder = ReelsOrder.builder()
                .order(order)
                .reel(new Reel())
                .amount(3)
                .build();
        Double expected = 3d;

        Mockito.when(modelMapper.map(dto, ReelsOrder.class)).thenReturn(reelsOrder);
        Mockito.when(reelsCartServiceImpl.listByUserId(order.getUser().getId())).thenReturn(List.of(response));

        Double actual = reelsOrderServiceImpl.add(order);

        assertEquals(expected,actual);

        Mockito.verify(reelsOrderRepository,Mockito.times(1)).save(reelsOrder);
        Mockito.verify(reelsCartServiceImpl,Mockito.times(1)).deleteByUserId(order.getUser().getId());
    }
}