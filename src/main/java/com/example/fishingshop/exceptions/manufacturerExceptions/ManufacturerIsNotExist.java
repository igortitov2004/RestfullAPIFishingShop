package com.example.fishingshop.exceptions.manufacturerExceptions;

public class ManufacturerIsNotExist extends RuntimeException{
    public ManufacturerIsNotExist(String message){
        super(message);
    }
}
