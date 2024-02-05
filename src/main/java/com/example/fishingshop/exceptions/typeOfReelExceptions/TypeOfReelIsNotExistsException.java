package com.example.fishingshop.exceptions.typeOfReelExceptions;

import com.example.fishingshop.exceptions.CustomException;

public class TypeOfReelIsNotExistsException extends CustomException {
    public TypeOfReelIsNotExistsException(String message) {
        super(message);
    }
}
