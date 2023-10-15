package ru.otus.spring.homework1.service;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import ru.otus.spring.homework1.domain.Answer;
import ru.otus.spring.homework1.domain.Question;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@DisplayName("Testing StreamsIOService service")
public class StreamsIOServiceTest {
    PrintStream mockPrintStream = Mockito.mock(PrintStream.class);
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final StreamsIOService streamsIOService = new StreamsIOService(new PrintStream(outputStream));

    @Test
    @DisplayName("Single line output testing")
    void shouldReturnTheSameString(){
        streamsIOService.printLine("Output line");
        verify(mockPrintStream).println("Output line");
        verifyNoMoreInteractions(mockPrintStream);
    }

    @Test
    @DisplayName("Question output testing")
    void shouldReturnStringWithQuestion(){
        Question testQuestion = new Question("Test question?", List.of(
                new Answer("Answer1", true), new Answer("Answer2", false)));
        streamsIOService.printQuestion(testQuestion);
        String printedOutput = outputStream.toString();
        assert printedOutput.contains("Test question? (1.Answer1,2.Answer2): ");
    }
}
