package com.example.fishingshop.DTOs.reelsCart;

import com.example.fishingshop.DTOs.ReelDTO;
import com.example.fishingshop.DTOs.UserDTO;
import com.example.fishingshop.models.Reel;
import com.example.fishingshop.models.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Data

public class ReelsCartDTO {

    private Long id;

    private UserDTO user;

    private ReelDTO reel;

    private Integer amount;
}
