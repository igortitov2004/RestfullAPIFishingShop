package com.example.fishingshop.DTOs.orders;

import com.example.fishingshop.DTOs.UserDTO;
import com.example.fishingshop.models.User;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private LocalDateTime localDateTime;
    private String address;
}
