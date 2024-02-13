package ru.otus.spring.bookstore.controllers;

import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.otus.spring.bookstore.exceptions.EntityNotFoundException;

import java.util.Map;

@org.springframework.web.bind.annotation.ControllerAdvice
@Data
public class ControllerAdvice {

    private long exceptionNotFoundCounter;

    private long totalExceptionsCounter;

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(EntityNotFoundException ex) {
        totalExceptionsCounter++;
        exceptionNotFoundCounter++;
        return ResponseEntity.badRequest().body(Map.of("id", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public void handleAnyOtherException(Exception ex) throws Exception {
        totalExceptionsCounter++;
        throw ex;
    }

}
