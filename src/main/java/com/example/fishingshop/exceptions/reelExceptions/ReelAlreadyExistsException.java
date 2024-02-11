package com.example.fishingshop.exceptions.reelExceptions;

import com.example.fishingshop.exceptions.CustomException;

public class ReelAlreadyExistsException extends CustomException {
    public ReelAlreadyExistsException(String message) {
        super(message);
    }
}
