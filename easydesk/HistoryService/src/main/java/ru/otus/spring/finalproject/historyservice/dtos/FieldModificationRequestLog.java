package ru.otus.spring.finalproject.historyservice.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class FieldModificationRequestLog {

    @NotEmpty
    private String name;

    @NotEmpty
    private String value;
}
