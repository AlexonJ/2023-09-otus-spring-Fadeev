package ru.otus.spring.finalproject.easydesk.dtos.errors;

import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class ErrorResponse {

    List<ErrorDetailed> errorList;

    public ErrorResponse(ErrorDetailed ... errorList) {
        this.errorList = Arrays.asList(errorList);
    }
}
