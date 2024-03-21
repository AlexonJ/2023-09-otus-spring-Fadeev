package ru.otus.spring.finalproject.easydesk.exceptions;

import lombok.Getter;

@Getter
public enum ErrorMessages {

    UNABLE_TO_READ_INPUT_MESSAGE(101, "An error occurred while read message: "),
    ERROR_VALIDATING_REQUEST(102, "An error occurred while validating request:"),

    TICKET_NOT_FOUND_BY_ID(201, "Ticket with id %s not found."),
    TICKET_NOT_FOUND_BY_CODE(202, "Ticket with code %s not found."),
    USER_NOT_FOUND_BY_USERNAME(210, "User with username %s not found"),

    ATTACHMENT_NOT_FOUND_BY_ID(220, "Attachment with id %s not found"),

    COMMENT_NOT_FOUND_BY_ID(230, "Comment with id %s not found"),

    FILE_UPLOADING_ERROR(250, "Error while uploading file: %s")

;


    public final Integer code;
    public final String message;

    ErrorMessages(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
