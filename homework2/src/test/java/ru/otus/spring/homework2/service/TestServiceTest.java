package ru.otus.spring.homework2.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.homework2.config.AppConfig;
import ru.otus.spring.homework2.dao.CsvQuestionDao;
import ru.otus.spring.homework2.domain.Answer;
import ru.otus.spring.homework2.domain.Question;
import ru.otus.spring.homework2.domain.Student;
import ru.otus.spring.homework2.domain.TestingResult;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

@DisplayName("Running TestService test")
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@ContextConfiguration(classes = {AppConfig.class, StreamsIOService.class})
public class TestServiceTest {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final ByteArrayInputStream inputStream = new ByteArrayInputStream("7\n3\n".getBytes());

    @Mock
    private CsvQuestionDao questionDao;

    private final IOService ioService = new StreamsIOService(new PrintStream(outputStream), inputStream);

    TestService testService = new TestServiceImpl(ioService, questionDao);

    @Test
    @DisplayName("Running TestService test")
    void testServiceTest(){

        List<Question> testQuestions = getTestQuestions();
        Mockito.when(questionDao.findAll()).thenReturn(testQuestions);

        TestingResult result = testService.executeTestForStudent(new Student("Ivanov", "Ivan"));

        var firstQuestion = Assertions.assertDoesNotThrow(() -> testQuestions.get(0));
        var correctAnswer = Assertions.assertDoesNotThrow(() ->
                firstQuestion.getAnswers().stream().filter(Answer::getIsCorrect).findFirst().orElseThrow());
        Assertions.assertEquals(result.getAnswers().get(firstQuestion), correctAnswer);
        Assertions.assertTrue(outputStream.toString().contains("What is the largest ocean on Earth?"));
        Assertions.assertTrue(outputStream.toString().contains("You choose incorrect option"));
    }

    List<Question> getTestQuestions() {
        return List.of(new Question("What is the largest ocean on Earth?",
                List.of(new Answer("Indian Ocean", false),
                        new Answer("Atlantic Ocean", false),
                        new Answer("Pacific Ocean", true)
                )
        ));
    }
}
