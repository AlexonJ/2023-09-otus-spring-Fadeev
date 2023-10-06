package ru.otus.spring.homework1.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Question {

    private String text;

    private List<Answer> answers;

}