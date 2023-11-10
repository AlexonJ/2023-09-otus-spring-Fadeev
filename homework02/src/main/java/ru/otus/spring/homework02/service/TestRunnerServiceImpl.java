package ru.otus.spring.homework02.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.homework02.domain.Student;
import ru.otus.spring.homework02.domain.TestingResult;

@RequiredArgsConstructor
@Service
public class TestRunnerServiceImpl implements TestRunnerService {

    private final TestService testService;

    private final StudentService studentService;

    private final ResultService resultService;

    private final IOService ioService;

    @Override
    public void run() {
        ioService.printLine("#### Welcome to the testing system ####");
        Student student = studentService.enterStudentData();
        TestingResult testResults = testService.executeTestForStudent(student);
        resultService.showResults(testResults);
    }
}
