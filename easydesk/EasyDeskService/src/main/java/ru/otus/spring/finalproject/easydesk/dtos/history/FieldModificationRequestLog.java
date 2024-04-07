package ru.otus.spring.finalproject.easydesk.dtos.history;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class FieldModificationRequestLog {

    @NotEmpty
    private String name;

    @NotEmpty
    private String value;
}
