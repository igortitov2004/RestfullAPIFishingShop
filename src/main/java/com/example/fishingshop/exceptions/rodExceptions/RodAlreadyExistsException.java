package com.example.fishingshop.exceptions.rodExceptions;

import com.example.fishingshop.exceptions.CustomException;

public class RodAlreadyExistsException extends CustomException {
    public RodAlreadyExistsException(String message) {
        super(message);
    }
}
