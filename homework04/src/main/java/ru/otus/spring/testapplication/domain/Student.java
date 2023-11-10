package ru.otus.spring.testapplication.domain;

import lombok.Data;

@Data
public class Student {

    private final String firstName;

    private final String lastName;

    public String getFullName() {
        return String.format("%s %s", firstName, lastName);
    }
}
