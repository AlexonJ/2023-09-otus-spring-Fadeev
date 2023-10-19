package ru.otus.spring.homework2.dao;

import ru.otus.spring.homework2.domain.Question;

import java.util.List;

public interface QuestionDao {

    List<Question> findAll();
}
