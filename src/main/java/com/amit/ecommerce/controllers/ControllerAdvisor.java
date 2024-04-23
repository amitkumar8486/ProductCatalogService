package com.amit.ecommerce.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvisor {
    /*
    There are several ways to handle exceptions in Spring Boot. One way is to use the @ControllerAdvice annotation.
    This annotation allows you to create a global exception handler that can catch and handle exceptions that occur in
    any controller in your application.
     */
    @ExceptionHandler({IllegalArgumentException.class, NullPointerException.class})
    public ResponseEntity<String> handleExceptions(Exception ex){
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
    }
}
