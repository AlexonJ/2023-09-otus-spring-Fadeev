package ru.otus.spring.finalproject.easydesk.exceptions;

public class EntityExistException extends RuntimeException{
    public EntityExistException (String message) {
        super(message);
    }
}
