package com.example.fishingshop.exceptions.orderExceptions;

import com.example.fishingshop.exceptions.CustomException;

public class OrderIsNotExistsException extends CustomException {
    public OrderIsNotExistsException(String message) {
        super(message);
    }
}
