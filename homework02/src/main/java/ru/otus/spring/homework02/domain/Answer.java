package ru.otus.spring.homework02.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Answer {

    private String text;

    private Boolean isCorrect;

}