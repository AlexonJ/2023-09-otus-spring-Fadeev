package ru.otus.spring.finalproject.easydesk.exceptions;

import lombok.Getter;

@Getter
public enum ErrorMessages {

    UNABLE_TO_READ_INPUT_MESSAGE(101, "An error occurred while read message: "),
    ERROR_VALIDATING_REQUEST(102, "An error occurred while validating request:"),

    TICKET_NOT_FOUND_BY_ID(201, "Ticket with id %s not found."),
    TICKET_NOT_FOUND_BY_CODE(202, "Ticket with code %s not found."),
    USER_NOT_FOUND_BY_USERNAME(210, "User with username %s not found"),
    USER_NOT_FOUND_BY_ID(211, "User with id %s not found"),
    USERS_NOT_FOUND_BY_CATEGORY(215, "Users for category %s not found"),

    ATTACHMENT_NOT_FOUND_BY_ID(220, "Attachment with id %s not found"),

    COMMENT_NOT_FOUND_BY_ID(230, "Comment with id %s not found"),

    INVALID_NEXT_WAYPOINT(240, "Waypoint %s is invalid. It can be only %s."),
    WAYPOINT_NOT_FOUND_BY_NAME(241, "Waypoint with name %s for process %s not found"),
    WAYPOINT_NOT_FOUND_BY_CATEGORY(242, "Waypoint for category %s not found"),
    END_OF_THE_PROCESS(243, "Waypoint cannot be changed, because current waypoint %s is end of the process %s"),

//    CURRENT_WAYPOINT_IS_EMPTY(242, "Current waypoint is empty"),

    FILE_UPLOADING_ERROR(260, "Error while uploading file: %s")

;


    public final Integer code;
    public final String message;

    ErrorMessages(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
