package ru.otus.spring.homework1.service;

import ru.otus.spring.homework1.domain.Question;

import java.io.PrintStream;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StreamsIOService implements IOService {
    private final PrintStream printStream;

    public StreamsIOService(PrintStream printStream) {
        this.printStream = printStream;
    }

    @Override
    public void printLine(String s) {
        printStream.println(s);
    }

    @Override
    public void printFormattedLine(String s, Object... args) {
        printStream.printf(s + "%n", args);
    }

    @Override
    public void printQuestions(List<Question> questions) {
        for (Question question: questions) {
            printQuestion(question);
        }
    }

    @Override
    public void printQuestion(Question question) {
        String answersString = IntStream.iterate(1, i -> i <= question.getAnswers().size(), i -> i + 1)
                .mapToObj(i -> i + "." + question.getAnswers().get(i - 1).getText())
                .collect(Collectors.joining(","));
        printFormattedLine(question.getText() + " (%s): ", answersString);
    }
}