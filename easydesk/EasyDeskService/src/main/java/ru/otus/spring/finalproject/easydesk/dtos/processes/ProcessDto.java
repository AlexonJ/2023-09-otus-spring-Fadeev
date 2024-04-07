package ru.otus.spring.finalproject.easydesk.dtos.processes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProcessDto {

    private long id;

    private String name;
}
