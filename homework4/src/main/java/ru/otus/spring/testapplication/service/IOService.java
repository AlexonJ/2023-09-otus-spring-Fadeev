package ru.otus.spring.testapplication.service;

import ru.otus.spring.testapplication.domain.Question;

public interface IOService {
    void printLine(String s);

    void printFormattedLine(String s, Object ...args);

    void printQuestionWithAnswers(Question question);

    String readStringWithPrompt(String prompt);

    int readIntForRange(int min, int max, String errorMessage);

    int readIntForRangeWithPrompt(int min, int max, String prompt, String errorMessage);

}
