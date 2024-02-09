package com.devesta.curricula.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ValidationExceptionHandler {
    Logger logger = LoggerFactory.getLogger(ValidationExceptionHandler.class);


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<String> errors = new ArrayList<>();

        result.getFieldErrors().forEach((error) ->
            errors.add(error.getField() + ": " + error.getDefaultMessage())
        );
        result.getGlobalErrors().forEach((error) ->
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage())
        );

        if(!errors.isEmpty())
            logger.error("Validation errors: " + errors);
        return ResponseEntity.badRequest().body(errors);
    }
}

