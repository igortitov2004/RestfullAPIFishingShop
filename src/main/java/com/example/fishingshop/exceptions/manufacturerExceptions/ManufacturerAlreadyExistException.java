package com.example.fishingshop.exceptions.manufacturerExceptions;

import com.example.fishingshop.exceptions.CustomException;

public class ManufacturerAlreadyExistException extends CustomException {
    public ManufacturerAlreadyExistException(String message){
        super(message);
    }
}
