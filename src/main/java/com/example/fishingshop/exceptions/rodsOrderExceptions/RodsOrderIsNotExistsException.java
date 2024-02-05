package com.example.fishingshop.exceptions.rodsOrderExceptions;

import com.example.fishingshop.exceptions.CustomException;

public class RodsOrderIsNotExistsException extends CustomException {
    public RodsOrderIsNotExistsException(String message){
        super(message);
    }
}
