package com.example.fishingshop.security.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
//    @Pattern(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$",message = "Incorrect email")

    @Pattern(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$",message = "Incorrect email")
   // @Pattern(regexp = "^[a-zA-Z0-9\\s\\-]+$",message = "Incorrect email")
    @Schema(defaultValue = "email")
    private String email;
    @Length(min = 3,max = 20,message = "Incorrect length of password. Min - 3, max - 20")
    @Schema(defaultValue = "pass")
    String password;
}
