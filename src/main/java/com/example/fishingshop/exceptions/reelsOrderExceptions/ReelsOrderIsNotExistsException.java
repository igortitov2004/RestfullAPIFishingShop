package com.example.fishingshop.exceptions.reelsOrderExceptions;

import com.example.fishingshop.exceptions.CustomException;

public class ReelsOrderIsNotExistsException extends CustomException {
    public ReelsOrderIsNotExistsException(String message) {
        super(message);
    }
}
