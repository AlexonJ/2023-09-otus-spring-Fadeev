package ru.otus.spring.testapplication.service;

import ru.otus.spring.testapplication.domain.Student;
import ru.otus.spring.testapplication.domain.TestingResult;

public interface TestService {

    TestingResult executeTestForStudent(Student student);
}