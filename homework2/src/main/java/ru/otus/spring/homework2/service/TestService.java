package ru.otus.spring.homework2.service;

import ru.otus.spring.homework2.domain.Student;
import ru.otus.spring.homework2.domain.TestingResult;

public interface TestService {

    TestingResult executeTestForStudent(Student student);
}