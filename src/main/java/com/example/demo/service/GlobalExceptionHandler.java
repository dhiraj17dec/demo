package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.dto.ErrorResponse;
import com.example.demo.exceptions.UserNotFoundException;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentException(MethodArgumentNotValidException ex) {
   	 ErrorResponse errorResponse = new ErrorResponse();
   	 Map<String, String> errors = new HashMap<String,String>();
	 ex.getBindingResult().getFieldErrors().forEach(error -> {
		 errors.put(error.getField(), error.getDefaultMessage());
	 });
	 errorResponse.setTimestamp(LocalDateTime.now());
	 errorResponse.setStatus(HttpStatus.BAD_REQUEST.toString());
	 errorResponse.setError("VALIDATION ERROR");
	 errorResponse.setErrors(errors);
	 
	 return ResponseEntity.badRequest().body(errorResponse);
    }
	
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
     logger.error("User Not Found Exception", ex);
     ErrorResponse errorResponse = new ErrorResponse();
   	 Map<String, String> errors = new HashMap<String,String>();
   	 errors.put("", ex.getMessage());
     errorResponse.setTimestamp(LocalDateTime.now());
	 errorResponse.setStatus(HttpStatus.NOT_FOUND.toString());
	 errorResponse.setError("DATA NOT FOUND ERROR");
	 errorResponse.setErrors(errors);
	 
	 return ResponseEntity.badRequest().body(errorResponse);
    }
    
    // Handle all other unhandled exceptions (fallback)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {
     logger.error("Unexpected Exception", ex);
     ErrorResponse errorResponse = new ErrorResponse();
     Map<String, String> errors = new HashMap<String,String>();
   	 errors.put("", ex.getMessage());
     errorResponse.setTimestamp(LocalDateTime.now());
   	 errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
	 errorResponse.setError("INTERNAL SERVER ERROR");
	 errorResponse.setErrors(errors);
	 
	 return ResponseEntity.badRequest().body(errorResponse);
    }
}