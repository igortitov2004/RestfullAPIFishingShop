package com.example.fishingshop.exceptions.reelExceptions;

import com.example.fishingshop.exceptions.CustomException;

public class ReelIsNotExistsException extends CustomException {
    public ReelIsNotExistsException(String message) {
        super(message);
    }
}
