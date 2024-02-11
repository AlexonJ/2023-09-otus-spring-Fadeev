package ru.otus.spring.bookstore.controllers;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.otus.spring.bookstore.exceptions.EntityNotFoundException;

import java.util.Map;

@org.springframework.web.bind.annotation.ControllerAdvice
@Data
public class ControllerAdvice {

    private final Counter exceptionNotFoundCounter;

    private final Counter totalExceptionsCounter;

    @Autowired
    public ControllerAdvice(MeterRegistry registry) {
        exceptionNotFoundCounter = Counter.builder("exceptions." + EntityNotFoundException.class.getSimpleName())
                .description("Number of exceptions of " + EntityNotFoundException.class.getSimpleName())
                .register(registry);
        totalExceptionsCounter = Counter.builder("exceptions." + EntityNotFoundException.class.getSimpleName())
                .description("Total number of exceptions")
                .register(registry);
    }


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(EntityNotFoundException ex) {
        totalExceptionsCounter.increment();
        exceptionNotFoundCounter.increment();
        return ResponseEntity.badRequest().body(Map.of("id", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public void handleAnyOtherException(Exception ex) throws Exception {
        totalExceptionsCounter.increment();
        throw ex;
    }

}
