package ru.otus.spring.homework02.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.homework02.dao.QuestionDao;
import ru.otus.spring.homework02.domain.Question;
import ru.otus.spring.homework02.domain.Student;
import ru.otus.spring.homework02.domain.TestingResult;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestingResult executeTestForStudent(Student student) {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        List<Question> questionList = questionDao.findAll();
        TestingResult testingResult = new TestingResult(student);
        for (Question question : questionList) {
            ioService.printQuestionWithAnswers(question);
            int itemNumber = ioService.readIntForRangeWithPrompt(1, question.getAnswers().size() + 1,
                    "Choose answer's number: ", "You choose incorrect option");
            testingResult.putAnswer(question, question.getAnswers().get(itemNumber - 1));
        }
        ioService.printLine("");

        return testingResult;
    }
}