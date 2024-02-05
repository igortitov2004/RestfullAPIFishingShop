package com.example.fishingshop.exceptions.manufacturerExceptions;

import com.example.fishingshop.exceptions.CustomException;

public class ManufacturerIsNotExistException extends CustomException {
    public ManufacturerIsNotExistException(String message){
        super(message);
    }
}
