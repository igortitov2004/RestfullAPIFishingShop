package com.example.fishingshop.DTOs.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Builder
public class ChangePasswordRequest {

    @Length(min = 3,max = 20,message = "Incorrect length of password. Min - 3, max - 20")
    @Schema(defaultValue = "current")
    private String currentPassword;
    @Length(min = 3,max = 20,message = "Incorrect length of password. Min - 3, max - 20")
    @Schema(defaultValue = "new")
    private String newPassword;
    @Length(min = 3,max = 20,message = "Incorrect length of password. Min - 3, max - 20")
    @Schema(defaultValue = "new again")
    private String confirmationPassword;
}
