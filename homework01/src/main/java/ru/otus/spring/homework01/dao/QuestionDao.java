package ru.otus.spring.homework01.dao;

import ru.otus.spring.homework01.domain.Question;

import java.util.List;

public interface QuestionDao {
    List<Question> findAll();
}
