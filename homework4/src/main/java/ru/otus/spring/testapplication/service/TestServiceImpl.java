package ru.otus.spring.testapplication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.testapplication.dao.QuestionDao;
import ru.otus.spring.testapplication.domain.Question;
import ru.otus.spring.testapplication.domain.Student;
import ru.otus.spring.testapplication.domain.TestingResult;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionDao questionDao;

    private final MessageService messageService;

    @Override
    public TestingResult executeTestForStudent(Student student) {
        ioService.printLine("");
        ioService.printFormattedLine(messageService.getMessage("test.answer-question") + "%n");
        List<Question> questionList = questionDao.findAll();
        TestingResult testingResult = new TestingResult(student);
        for (Question question : questionList) {
            ioService.printQuestionWithAnswers(question);
            int itemNumber = ioService.readIntForRangeWithPrompt(1, question.getAnswers().size() + 1,
                    messageService.getMessage("test.choose-answer"),
                    messageService.getMessage("test.choose-answer-error"));
            testingResult.putAnswer(question, question.getAnswers().get(itemNumber - 1));
        }
        ioService.printLine("");

        return testingResult;
    }
}