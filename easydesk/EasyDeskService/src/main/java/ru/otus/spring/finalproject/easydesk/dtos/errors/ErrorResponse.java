package ru.otus.spring.finalproject.easydesk.dtos.errors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ErrorResponse {

    List<ErrorDetailed> errorList;

    public ErrorResponse(ErrorDetailed ... errorList) {
        this.errorList = Arrays.asList(errorList);
    }
}
