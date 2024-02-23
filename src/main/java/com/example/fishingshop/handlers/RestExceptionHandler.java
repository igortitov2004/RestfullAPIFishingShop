package com.example.fishingshop.handlers;

import org.modelmapper.ValidationException;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorMessage> handle(UsernameNotFoundException ex) {
        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage());
        errorMessage.setMessage("custom: Username not found");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorMessage);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorMessage> handle(BadCredentialsException ex) {
        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage());
        errorMessage.setMessage("custom: Bad credentials");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorMessage);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorMessage> handle(AccessDeniedException ex) {
        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage());
        errorMessage.setMessage("custom: Access denied");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorMessage);
    }
}