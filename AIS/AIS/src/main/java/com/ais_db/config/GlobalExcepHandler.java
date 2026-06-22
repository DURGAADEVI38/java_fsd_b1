package com.ais_db.config;

import com.ais_db.exception.ResourceNotFoundException;
import com.ais_db.exception.UserAlreadyExistsException;
import com.ais_db.util.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExcepHandler {
    private ResponseUtil responseUtil;
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseUtil> handleResurceNotFoundException(ResourceNotFoundException e)
    {
        responseUtil.setMessage(e.getMessage());
        return ResponseEntity.badRequest().body(responseUtil);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleMethodNotValidException(MethodArgumentNotValidException e)
    {
        BindingResult bindingResult=e.getBindingResult();
        List<FieldError> errors=bindingResult.getFieldErrors();
        Map<String,String> map=new HashMap<>();
        for(FieldError err:errors)
        {
            map.put(err.getField(),err.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(map);

    }
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ResponseUtil> handleUserAlreadyPresentException(
            UserAlreadyExistsException e
    ){
        responseUtil.setMessage(e.getMessage());
        return ResponseEntity
                .badRequest()
                .body(responseUtil);
    }
}
