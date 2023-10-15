package ru.otus.spring.homework1.service;

import lombok.RequiredArgsConstructor;
import ru.otus.spring.homework1.dao.QuestionDao;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public void executeTest() {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        ioService.printQuestions(questionDao.findAll());
        ioService.printLine("");
    }
}