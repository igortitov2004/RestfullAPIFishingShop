package com.example.fishingshop.handlers;

import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handle(Exception ex){
        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage());
        if(ex instanceof UsernameNotFoundException){
            errorMessage.setMessage("custom: Username not found ");
        }
        if(ex instanceof BadCredentialsException){
            errorMessage.setMessage("custom: Bad credentials");
        }
        if(ex instanceof AccessDeniedException){
            errorMessage.setMessage("custom: Access denied");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorMessage);
    }
}
