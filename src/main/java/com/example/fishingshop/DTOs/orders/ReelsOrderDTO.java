package com.example.fishingshop.DTOs.orders;

import com.example.fishingshop.DTOs.ReelDTO;
import com.example.fishingshop.DTOs.UserDTO;
import com.example.fishingshop.models.Reel;
import com.example.fishingshop.models.User;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class ReelsOrderDTO {

    private Long id;

    private ReelDTO reel;

    private Integer amount;


}
