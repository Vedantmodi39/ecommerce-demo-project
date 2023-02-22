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
    @ExceptionHandler(value = ProductSkuNotFoundException.class)
    public ResponseEntity<Object> exception(ProductSkuNotFoundException exception) {
        log.error("handling ProductSkuNotFoundException...");
        return new ResponseEntity<>(new GenericResponse(false,  exception.getMessage() + " Product Not Exist", new EmptyJsonBody(), HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = CategoryNotExistException.class)
    public ResponseEntity<Object> exception(CategoryNotExistException exception) {
        log.error("handling CategoryNotExistException...");
        return new ResponseEntity<>(new GenericResponse(false,  exception.getMessage() + " Category Not Exist", new EmptyJsonBody(), HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ProductDeletedException.class)
    public ResponseEntity<Object> exception(ProductDeletedException exception) {
        log.error("handling ProductDeletedException...");
        return new ResponseEntity<>(new GenericResponse(false, "Product" +exception.getMessage() + "is Deleted", new EmptyJsonBody(), HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value = ProductIdNotFoundException.class)
    public ResponseEntity<Object> exception(ProductIdNotFoundException exception) {
        log.error("handling ProductIdNotFoundException...");
        return new ResponseEntity<>(new GenericResponse(false, "Product Id "+exception.getMessage() +"Is Not Exist" , new EmptyJsonBody(), HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = InvalidDataException.class)
    public ResponseEntity<Object> exception(InvalidDataException exception) {
        log.error("handling InvalidDataException...");
        return new ResponseEntity<>(new GenericResponse(false, exception.getMessage(), new EmptyJsonBody(), HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value = CategoryAlreadyExistsException.class)
    public ResponseEntity<Object> exception(CategoryAlreadyExistsException exception) {
        log.error("handling CategoryAlredyExistsException...");
        return new ResponseEntity<>(new GenericResponse(false, exception.getMessage(), new EmptyJsonBody(), HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
    }
}
