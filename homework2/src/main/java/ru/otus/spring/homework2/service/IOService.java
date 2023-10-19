package ru.otus.spring.homework2.service;

import ru.otus.spring.homework2.domain.Question;

public interface IOService {
    void printLine(String s);

    void printFormattedLine(String s, Object ...args);

    void printQuestionWithAnswers(Question question);

    String readStringWithPrompt(String prompt);

    int readIntForRange(int min, int max, String errorMessage);

    int readIntForRangeWithPrompt(int min, int max, String prompt, String errorMessage);

}
