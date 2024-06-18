package com.example.fishingshop.DTOs.orders;

import com.example.fishingshop.DTOs.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private Long id;
    private UserDTO user;
    private String localDateTime;
    private String address;
    private Double totalPrice;
}
