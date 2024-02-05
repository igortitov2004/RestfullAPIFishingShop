package com.example.fishingshop.DTOs;

import com.example.fishingshop.models.Rod;
import com.example.fishingshop.models.User;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class RodsOrderDTO {
    private Long id;
    private UserDTO user;
    private RodDTO rod;
    private Integer amount;
    private Date date;
    private String address;
}
