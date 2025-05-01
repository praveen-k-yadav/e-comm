package com.ecommerce.project.exceptions;

//Class to have custom error response whenever any exception is thrown

import com.ecommerce.project.payload.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice

public class MyGlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> myMethodArgumentNotValidException(MethodArgumentNotValidException e)
    {
        Map<String,String> response= new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(err -> {
            String fieldName=((FieldError)err).getField();
            String message= err.getDefaultMessage();
            response.put(fieldName,message);
        });
        return new ResponseEntity<Map<String,String>>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFounException.class)
    public ResponseEntity<APIResponse> myResourceNotFoundException(ResourceNotFounException e)
    {
     String message = e.getMessage();
     APIResponse apiResponse = new APIResponse(message,false);
     return  new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<APIResponse> myAPIException(APIException e)
    {
        String message = e.getMessage();
        APIResponse apiResponse =  new APIResponse(message,false);
        return  new ResponseEntity<>(apiResponse,HttpStatus.BAD_REQUEST);
    }
}
