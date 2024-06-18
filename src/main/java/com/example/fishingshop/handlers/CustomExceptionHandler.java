package com.example.fishingshop.handlers;

import com.example.fishingshop.exceptions.CustomException;
import com.example.fishingshop.exceptions.ErrorResponse;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.*;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorMessage> handleException(CustomException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorResponse>> handleException(MethodArgumentNotValidException ex) {
        List<ErrorResponse> list =  ex.getFieldErrors().stream().map(e->new ErrorResponse(e.getField(),e.getDefaultMessage())).toList();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(list);
    }
}
