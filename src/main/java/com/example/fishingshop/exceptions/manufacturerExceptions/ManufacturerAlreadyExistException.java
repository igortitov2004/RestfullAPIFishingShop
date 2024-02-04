package com.example.fishingshop.exceptions.manufacturerExceptions;

public class ManufacturerAlreadyExistException extends RuntimeException{
    public ManufacturerAlreadyExistException(String message){
        super(message);
    }
}
