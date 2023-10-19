package ru.otus.spring.homework2.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@RequiredArgsConstructor
public class TestingResult {

    private final Student student;

    private final List<Question> answeredQuestions = new ArrayList<>();

    private final Map<Question, Answer> answers = new HashMap<>();

    public void putAnswer(Question question, Answer answer) {
        answeredQuestions.add(question);
        answers.put(question, answer);
    }

}
