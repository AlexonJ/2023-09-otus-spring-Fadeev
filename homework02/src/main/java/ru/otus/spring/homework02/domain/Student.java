package ru.otus.spring.homework02.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Student {

    private final String firstName;

    private final String lastName;

    public String getFullName() {
        return String.format("%s %s", firstName, lastName);
    }
}
