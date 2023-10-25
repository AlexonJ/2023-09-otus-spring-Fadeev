package ru.otus.spring.testapplication.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.testapplication.dao.CsvQuestionDao;
import ru.otus.spring.testapplication.domain.Answer;
import ru.otus.spring.testapplication.domain.Question;
import ru.otus.spring.testapplication.domain.Student;
import ru.otus.spring.testapplication.domain.TestingResult;

import java.util.List;

@DisplayName("Running TestService test")
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class TestServiceTest {
    @Mock
    private CsvQuestionDao questionDao;

    @Mock
    private IOService ioService;

    @Mock
    private MessageService messageService;

    @InjectMocks
    private TestServiceImpl testService;

    @BeforeEach
    void setUp(){
        setUpMessageServiceMock();
        setUpIOServiceMock();
    }

    @Test
    @DisplayName("Running TestService test")
    void testServiceTest(){

        List<Question> testQuestions = getTestQuestions();

        Mockito.when(questionDao.findAll()).thenReturn(testQuestions);
        ArgumentCaptor<Question> argumentCaptor = ArgumentCaptor.forClass(Question.class);

        TestingResult result = testService.executeTestForStudent(new Student("Ivanov", "Ivan"));

        var firstQuestion = Assertions.assertDoesNotThrow(() -> testQuestions.get(0));
        var correctAnswer = Assertions.assertDoesNotThrow(() ->
                firstQuestion.getAnswers().stream().filter(Answer::getIsCorrect).findFirst().orElseThrow());
        Assertions.assertEquals(result.getAnswers().get(firstQuestion), correctAnswer);
        Mockito.verify(ioService, Mockito.atLeastOnce()).printQuestionWithAnswers(argumentCaptor.capture());
        Assertions.assertTrue(argumentCaptor.getAllValues().stream().anyMatch(s -> s.equals(firstQuestion)));
    }


    private List<Question> getTestQuestions() {
        return List.of(new Question("What is the largest ocean on Earth?",
                List.of(new Answer("Indian Ocean", false),
                        new Answer("Atlantic Ocean", false),
                        new Answer("Pacific Ocean", true)
                )
        ));
    }

    private void setUpMessageServiceMock(){
        Mockito.when(messageService.getMessage("test.answer-question")).thenReturn("Please answer the questions below:");
        Mockito.when(messageService.getMessage("test.choose-answer")).thenReturn("Choose answer's number:");
        Mockito.when(messageService.getMessage("test.choose-answer-error")).thenReturn("You choose incorrect option");
    }

    private void setUpIOServiceMock(){
        Mockito.when(ioService.readIntForRangeWithPrompt(Mockito.anyInt(), Mockito.anyInt(),
                Mockito.anyString(), Mockito.anyString())).thenReturn(3);
    }
}
