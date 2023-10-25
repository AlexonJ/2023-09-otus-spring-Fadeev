package ru.otus.spring.testapplication.dao;

import ru.otus.spring.testapplication.domain.Question;

import java.util.List;

public interface QuestionDao {

    List<Question> findAll();
}
