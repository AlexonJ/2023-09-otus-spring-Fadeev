package ru.otus.spring.finalproject.easydesk.exceptions;

public class FieldValueParseException extends RuntimeException {
    public FieldValueParseException (String message){
        super(message);
    }
}
