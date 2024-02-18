package com.springboot.Wishlist.management.system.exception;

import com.springboot.Wishlist.management.system.dto.response.GeneralResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<GeneralResponse> ExceptionHandler(Exception e){
        GeneralResponse response = new GeneralResponse();
        response.setMessage(e.getMessage());
        response.setSuccess(false);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
