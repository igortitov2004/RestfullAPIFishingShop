package com.example.fishingshop.exceptions.userExceptions;

import com.example.fishingshop.exceptions.CustomException;

public class UserAlreadyExistsException extends CustomException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
