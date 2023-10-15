package ru.otus.spring.homework1.dao;

import ru.otus.spring.homework1.domain.Question;

import java.util.List;

public interface QuestionDao {
    List<Question> findAll();
}
