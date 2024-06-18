package com.example.fishingshop.security.auth;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(defaultValue = "email")
    private String email;
    @Pattern(regexp = "^[a-zA-Zа-яёА-ЯЁ0-9\\s\\-]+$",message = "Incorrect surname")
    @Schema(defaultValue = "string")
    private String surname;
    @Pattern(regexp = "^[a-zA-Zа-яёА-ЯЁ0-9\\s\\-]+$",message = "Incorrect first name")
    @Schema(defaultValue = "string")
    private String firstName;
    @Pattern(regexp = "^[a-zA-Zа-яёА-ЯЁ0-9\\s\\-]+$",message = "Incorrect patronymic")
    @Schema(defaultValue = "string")
    private String patronymic;
    @Length(min = 3,max = 20, message = "Incorrect length of password. Min - 3, max - 20")
    @Schema(defaultValue = "pass")
    private String password;
}
