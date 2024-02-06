package com.example.fishingshop.exceptions.userExceptions;

import com.example.fishingshop.exceptions.CustomException;

public class UserIsNotExistsException extends CustomException {
    public UserIsNotExistsException(String message) {
        super(message);
    }
}
