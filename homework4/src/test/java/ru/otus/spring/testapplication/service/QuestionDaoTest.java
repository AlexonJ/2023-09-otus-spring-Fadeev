package ru.otus.spring.testapplication.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.testapplication.config.AppConfig;
import ru.otus.spring.testapplication.dao.CsvQuestionDao;
import ru.otus.spring.testapplication.dao.QuestionDao;
import ru.otus.spring.testapplication.dao.dto.Mapper;
import ru.otus.spring.testapplication.domain.Answer;
import ru.otus.spring.testapplication.domain.Question;

import java.util.List;

@DisplayName("Running QuestionDao class integration test")
@SpringBootTest(classes = {CsvQuestionDao.class, FileServiceImpl.class, Mapper.class})
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@EnableConfigurationProperties(AppConfig.class)
public class QuestionDaoTest {

    @Autowired
    AppConfig appConfig;

    @Autowired
    QuestionDao questionDao;

    @Test
    @DisplayName("Running QuestionDao class integration test")
    void questionDaoTest(){
        List<Question> questionList = Assertions.assertDoesNotThrow(() -> questionDao.findAll());
        Assertions.assertEquals(2, questionList.size());
        Assertions.assertTrue(questionList.get(1).getText().contains("What is the largest ocean on Earth?"));
        Answer answer = Assertions.assertDoesNotThrow(() -> questionList.get(1).getAnswers().get(1));
        Assertions.assertTrue(answer.getText().contains("Atlantic Ocean"));
    }
}
