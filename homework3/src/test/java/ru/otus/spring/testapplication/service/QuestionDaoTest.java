package ru.otus.spring.testapplication.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.testapplication.config.AppConfig;
import ru.otus.spring.testapplication.dao.CsvQuestionDao;
import ru.otus.spring.testapplication.dao.QuestionDao;
import ru.otus.spring.testapplication.dao.dto.Mapper;
import ru.otus.spring.testapplication.domain.Answer;
import ru.otus.spring.testapplication.domain.Question;

import java.util.List;

@DisplayName("Running QuestionDao class integration test")
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:application.yml")
@ActiveProfiles("test")
@ContextConfiguration(classes = {AppConfig.class, CsvQuestionDao.class, FileServiceImpl.class, Mapper.class})
public class QuestionDaoTest {

    @Autowired
    AppConfig appConfig;

    @Autowired
    QuestionDao questionDao;

    @Test
    @DisplayName("Running QuestionDao class integration test")
    void questionDaoTest(){
        appConfig.setTestFileName("question-data/questions-test_en.csv");
        List<Question> questionList = Assertions.assertDoesNotThrow(() -> questionDao.findAll());
        Assertions.assertEquals(2, questionList.size());
        Assertions.assertTrue(questionList.get(1).getText().contains("What is the largest ocean on Earth?"));
        Answer answer = Assertions.assertDoesNotThrow(() -> questionList.get(1).getAnswers().get(1));
        Assertions.assertTrue(answer.getText().contains("Atlantic Ocean"));
    }
}
