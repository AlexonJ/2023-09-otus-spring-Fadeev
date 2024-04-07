package ru.otus.spring.finalproject.easydesk.exceptions;

import lombok.Getter;

@Getter
public class InternalServerErrorException extends RuntimeException{

    private Integer errorCode;

    private String message;

    public InternalServerErrorException(Integer errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }
}
