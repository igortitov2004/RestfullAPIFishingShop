package com.example.fishingshop.security.auth;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @Pattern(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$",message = "Incorrect email")
    private String email;
    @Length(min = 3,max = 20, message = "Incorrect length of password. Min - 3, max - 20")
    private String password;
    @Pattern(regexp = "^[a-zA-Z0-9\\s\\-]+$",message = "Incorrect surname")
    private String surname;
    @Pattern(regexp = "^[a-zA-Z0-9\\s\\-]+$",message = "Incorrect first name")
    private String firstName;
    @Pattern(regexp = "^[a-zA-Z0-9\\s\\-]+$",message = "Incorrect patronymic")
    private String patronymic;
}
