package ru.otus.spring.testapplication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import ru.otus.spring.testapplication.domain.Student;
import ru.otus.spring.testapplication.domain.TestingResult;

@RequiredArgsConstructor
@Service
public class TestRunnerServiceImpl implements CommandLineRunner {

    private final TestService testService;

    private final StudentService studentService;

    private final ResultService resultService;

    private final IOService ioService;

    private final MessageService messageService;

    @Override
    public void run(String ... args) {
        ioService.printLine(messageService.getMessage("general.welcome"));
        Student student = studentService.enterStudentData();
        TestingResult testResults = testService.executeTestForStudent(student);
        resultService.showResults(testResults);
    }
}
