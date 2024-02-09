package com.example.fishingshop.DTOs;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String email;
    private String surname;
    private String firstName;
    private String patronymic;
}
