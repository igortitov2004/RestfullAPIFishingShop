package com.example.fishingshop.exceptions.rodsCartExceptions;

import com.example.fishingshop.exceptions.CustomException;

public class RodsCartIsNotExistsException extends CustomException {
    public RodsCartIsNotExistsException(String message){
        super(message);
    }
}
