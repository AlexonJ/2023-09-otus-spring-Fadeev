package ru.otus.spring.homework2.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
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
