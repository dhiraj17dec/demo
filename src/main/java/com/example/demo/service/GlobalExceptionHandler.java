package com.example.demo.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.demo.dto.ErrorResponse;
import com.example.demo.exceptions.UserNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
   	 ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.toString(), ex.getMessage());
     return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    // Handle all other unhandled exceptions (fallback)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {
    	 System.out.println(ex);
    	 ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "An unexpected error occurred.");
         return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}