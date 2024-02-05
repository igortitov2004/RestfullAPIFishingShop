package com.example.fishingshop.exceptions.rodExceptions;

import com.example.fishingshop.exceptions.CustomException;

public class RodIsNotExistException extends CustomException {
    public RodIsNotExistException(String message){
        super(message);
    }
}
