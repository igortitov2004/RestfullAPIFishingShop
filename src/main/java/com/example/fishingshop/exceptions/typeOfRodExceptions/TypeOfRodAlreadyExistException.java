package com.example.fishingshop.exceptions.typeOfRodExceptions;

import com.example.fishingshop.exceptions.CustomException;

public class TypeOfRodAlreadyExistException extends CustomException {
    public TypeOfRodAlreadyExistException(String message){
        super(message);
    }
}
