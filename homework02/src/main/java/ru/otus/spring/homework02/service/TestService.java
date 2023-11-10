package ru.otus.spring.homework02.service;

import ru.otus.spring.homework02.domain.Student;
import ru.otus.spring.homework02.domain.TestingResult;

public interface TestService {

    TestingResult executeTestForStudent(Student student);
}