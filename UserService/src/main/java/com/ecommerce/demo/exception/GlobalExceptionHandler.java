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
    @ExceptionHandler(value = UserAlreadyExistException.class)
    public ResponseEntity<Object> exception(UserAlreadyExistException exception) {
        log.error("handling UserAlreadyExistException...");
        return new ResponseEntity<>(new GenericResponse(false, "User Already Register " + exception.getMessage() , new EmptyJsonBody(), HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<Object> exception(UserNotFoundException userNotFoundException){
        log.error("handling UserNotFoundException...");
        return new ResponseEntity<>(new GenericResponse(false,"User Not Found ",new EmptyJsonBody(), HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
    }


}
