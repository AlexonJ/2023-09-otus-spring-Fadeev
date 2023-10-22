package ru.otus.spring.testapplication.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.spring.testapplication.domain.Answer;
import ru.otus.spring.testapplication.domain.Question;
import ru.otus.spring.testapplication.service.StreamsIOService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

@DisplayName("Testing StreamsIOService service")
public class StreamsIOServiceTest {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final ByteArrayInputStream inputStream = new ByteArrayInputStream("".getBytes());
    private final StreamsIOService streamsIOService = new StreamsIOService(new PrintStream(outputStream), inputStream);

    @Test
    @DisplayName("Single line output testing")
    void shouldReturnTheSameString(){
        streamsIOService.printLine("Output line");
        Assertions.assertTrue(outputStream.toString().contains("Output line"));
    }

    @Test
    @DisplayName("Question output testing")
    void shouldReturnStringWithQuestion(){
        Question testQuestion = new Question("Test question?", List.of(
                new Answer("Answer1", true), new Answer("Answer2", false)));
        streamsIOService.printQuestionWithAnswers(testQuestion);
        Assertions.assertTrue(outputStream.toString().contains("Test question? (1.Answer1,2.Answer2): "));
    }

}
