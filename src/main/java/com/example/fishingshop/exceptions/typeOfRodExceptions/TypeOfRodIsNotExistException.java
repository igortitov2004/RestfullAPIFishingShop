package com.example.fishingshop.exceptions.typeOfRodExceptions;

import com.example.fishingshop.exceptions.CustomException;

public class TypeOfRodIsNotExistException extends CustomException {
    public TypeOfRodIsNotExistException(String message){
        super(message);
    }
}
