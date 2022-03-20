package com.github.guitsilva.rebelsapi.handlers;

import com.github.guitsilva.rebelsapi.exceptions.InvalidTradeException;
import com.github.guitsilva.rebelsapi.exceptions.RebelNotFoundException;
import com.github.guitsilva.rebelsapi.exceptions.RebelOverwriteException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RequestValidationHandler extends ResponseEntityExceptionHandler{

    @ExceptionHandler(RebelOverwriteException.class)
    public ResponseEntity<Object> handleRebelNotFound(
            RebelOverwriteException exception
    ) {
        Map<String, String> errors = new HashMap<>();

        errors.put("message", exception.getLocalizedMessage());

        return new ResponseEntity<>(errors, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(RebelNotFoundException.class)
    public ResponseEntity<Object> handleRebelNotFound(
            RebelNotFoundException exception
    ) {
        Map<String, String> errors = new HashMap<>();

        errors.put("message", exception.getLocalizedMessage());

        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidTradeException.class)
    public ResponseEntity<Object> handleInvalidTrade(
            InvalidTradeException exception
    ) {
        Map<String, String> errors = new HashMap<>();

        errors.put("message", exception.getLocalizedMessage());

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult().getAllErrors().forEach((error) ->{
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
