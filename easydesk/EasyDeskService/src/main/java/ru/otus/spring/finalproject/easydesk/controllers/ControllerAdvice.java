package ru.otus.spring.finalproject.easydesk.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.otus.spring.finalproject.easydesk.dtos.errors.ErrorDetailed;
import ru.otus.spring.finalproject.easydesk.dtos.errors.ErrorResponse;
import ru.otus.spring.finalproject.easydesk.exceptions.CommonValidationException;
import ru.otus.spring.finalproject.easydesk.exceptions.EntityExistException;
import ru.otus.spring.finalproject.easydesk.exceptions.EntityNotFoundException;
import ru.otus.spring.finalproject.easydesk.exceptions.ErrorMessages;
import ru.otus.spring.finalproject.easydesk.exceptions.FieldValueParseException;
import ru.otus.spring.finalproject.easydesk.exceptions.InternalServerErrorException;

import java.util.stream.Collectors;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler({
            FieldValueParseException.class,
            EntityExistException.class,
            EntityNotFoundException.class,
            CommonValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleClientErrorException(RuntimeException ex) {

        var errorDetailed = ErrorDetailed.builder();
        if (ex instanceof FieldValueParseException) {
            errorDetailed.code(101);
        } else if (ex instanceof EntityExistException) {
            errorDetailed.code(102);
        } else if (ex instanceof EntityNotFoundException) {
            errorDetailed.code(103);
        } else if (ex instanceof CommonValidationException) {
            errorDetailed.code(((CommonValidationException) ex).getErrorCode());
        }

        errorDetailed.message(ex.getMessage());
        return ResponseEntity.badRequest().body(new ErrorResponse(errorDetailed.build()));
    }

    @ExceptionHandler({InternalServerErrorException.class})
    public ResponseEntity<ErrorResponse> handleInternalErrorExceptions(RuntimeException ex) {
        var errorDetailed = ErrorDetailed.builder()
                .code(((CommonValidationException) ex).getErrorCode())
                .message(ex.getMessage());
        var errorResponse = new ErrorResponse(errorDetailed.build());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> handleValueParseException(MethodArgumentNotValidException ex) {

        String errorMessage = ErrorMessages.ERROR_VALIDATING_REQUEST.getMessage() + ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> "Field: %s, value: %s, message: %s".formatted(
                        fieldError.getField(),
                        fieldError.getRejectedValue() != null ? fieldError.getRejectedValue().toString() : "null",
                        fieldError.getDefaultMessage())).collect(Collectors.joining("\n"));

        var errorDetailed = ErrorDetailed.builder()
                .code(ErrorMessages.ERROR_VALIDATING_REQUEST.getCode())
                .message(errorMessage).build();
        return ResponseEntity.badRequest().body(new ErrorResponse(errorDetailed));

    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<ErrorResponse> handleValueParseException(HttpMessageNotReadableException ex) {
        var errorDetailed = ErrorDetailed.builder()
                .message(ErrorMessages.UNABLE_TO_READ_INPUT_MESSAGE.getMessage() + ex.getMessage())
                .code(ErrorMessages.UNABLE_TO_READ_INPUT_MESSAGE.getCode()).build();
        return ResponseEntity.badRequest().body(new ErrorResponse(errorDetailed));

    }
}
