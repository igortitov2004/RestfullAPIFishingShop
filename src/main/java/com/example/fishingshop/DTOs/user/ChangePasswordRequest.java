package com.example.fishingshop.DTOs.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Builder
public class ChangePasswordRequest {

    @Length(min = 3,max = 20)
    private String currentPassword;
    @Length(min = 3,max = 20)
    private String newPassword;
    @Length(min = 3,max = 20)
    private String confirmationPassword;
}
