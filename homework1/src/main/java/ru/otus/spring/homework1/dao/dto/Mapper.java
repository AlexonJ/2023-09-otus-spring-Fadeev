package ru.otus.spring.homework1.dao.dto;

import org.springframework.stereotype.Component;
import ru.otus.spring.homework1.domain.Question;

@Component
public class Mapper {
    public Question questionDtoToQuestion(QuestionDto questionDto){
        Question question = new Question();
        question.setText(questionDto.getText());
        question.setAnswers(questionDto.getAnswers());
        return question;
    }
}
