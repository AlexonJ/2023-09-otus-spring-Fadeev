package ru.otus.spring.homework01.service;

import ru.otus.spring.homework01.domain.Question;

import java.util.List;

public interface IOService {
    void printLine(String s);

    void printFormattedLine(String s, Object ...args);

    void printQuestion(Question question);

    void printQuestions(List<Question> questions);
}
