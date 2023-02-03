package com.ecommerce.demo.exception;

import com.ecommerce.demo.dto.EmptyJsonBody;
import com.ecommerce.demo.dto.GenericResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(value = ProductAlreadyExistException.class)
    public ResponseEntity<Object> exception(ProductAlreadyExistException exception) {
        log.error("handling ProductAlreadyExistException...");
        return new ResponseEntity<>(new GenericResponse(false, "This "+exception.getMessage() +" SKU Number Already Exist " , new EmptyJsonBody(), HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value = CategoryNotExistException.class)
    public ResponseEntity<Object> exception(CategoryNotExistException exception) {
        log.error("handling CategoryNotExistException...");
        return new ResponseEntity<>(new GenericResponse(false,  exception.getMessage() + " Category Not Exist", new EmptyJsonBody(), HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
    }
}
