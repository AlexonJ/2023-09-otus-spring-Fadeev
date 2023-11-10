package ru.otus.spring.homework02.dao;

import ru.otus.spring.homework02.domain.Question;

import java.util.List;

public interface QuestionDao {

    List<Question> findAll();
}
