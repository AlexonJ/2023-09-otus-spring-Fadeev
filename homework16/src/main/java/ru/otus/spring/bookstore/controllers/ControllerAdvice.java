package ru.otus.spring.bookstore.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.otus.spring.bookstore.exceptions.EntityNotFoundException;

import java.util.Map;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(EntityNotFoundException ex) {
        return ResponseEntity.badRequest().body(Map.of("id", ex.getMessage()));
    }

}
