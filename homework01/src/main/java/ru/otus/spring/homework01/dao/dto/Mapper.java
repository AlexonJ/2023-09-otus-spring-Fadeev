package ru.otus.spring.homework01.dao.dto;

import org.springframework.stereotype.Component;
import ru.otus.spring.homework01.domain.Question;

@Component
public class Mapper {
    public Question questionDtoToQuestion(QuestionDto questionDto) {
        Question question = new Question();
        question.setText(questionDto.getText());
        question.setAnswers(questionDto.getAnswers());
        return question;
    }
}
