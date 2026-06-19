package com.proj.demo.config;

import com.proj.demo.exception.FileInvalidExtensionException;
import com.proj.demo.exception.ResourcenotFoundException;
import com.proj.demo.util.ResponseUtil;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
@AllArgsConstructor
public class GlobalExceptionHandler {
    private ResponseUtil responseUtil;

    @ExceptionHandler(ResourcenotFoundException.class)
    public ResponseEntity<ResponseUtil> handleResourceNotFoundExceptoin(ResourcenotFoundException e)
    {

        responseUtil.setMessage(e.getMessage());
        return ResponseEntity.badRequest().body(responseUtil);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleMethodArgumentNotValid(MethodArgumentNotValidException e, Principal principal)
    {

        BindingResult bindingResult=e.getBindingResult();
        List<FieldError> errors=bindingResult.getFieldErrors();
        Map<String,String> map=new HashMap<>();
        for(FieldError err:errors)
        {
            map.put(err.getField(),err.getDefaultMessage());

        }
        return ResponseEntity
                .badRequest()
                .body(map);

    }
    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<ResponseUtil> handleFileNotFoundException(
            FileNotFoundException e
    ){
        responseUtil.setMessage(e.getMessage());
        return ResponseEntity
                .badRequest()
                .body(responseUtil);
    }
    @ExceptionHandler(IOException.class)
    public ResponseEntity<ResponseUtil> handleIOException(
            IOException e
    ){
        responseUtil.setMessage(e.getMessage());
        return ResponseEntity
                .badRequest()
                .body(responseUtil);
    }
    @ExceptionHandler(FileInvalidExtensionException.class)
    public ResponseEntity<ResponseUtil> handleFileInvalidExtensionException(
            FileInvalidExtensionException e
    ){
        responseUtil.setMessage(e.getMessage());
        return ResponseEntity
                .badRequest()
                .body(responseUtil);
    }
}
