package ru.otus.spring.homework1.service;

import ru.otus.spring.homework1.domain.Question;

import java.util.List;

public interface IOService {
    void printLine(String s);

    void printFormattedLine(String s, Object ...args);

    void printQuestion(Question question);

    void printQuestions(List<Question> questions);
}
