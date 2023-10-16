package ru.otus.spring.homework2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.homework2.config.TestConfig;
import ru.otus.spring.homework2.domain.Answer;
import ru.otus.spring.homework2.domain.Question;
import ru.otus.spring.homework2.domain.TestingResult;

@RequiredArgsConstructor
@Service
public class ResultServiceImpl implements ResultService {

    private final TestConfig testConfig;

    private final StreamsIOService ioService;

    @Override
    public void showResults(TestingResult result) {
        ioService.printLine("");
        ioService.printFormattedLine("#### Student %s testing results ####", result.getStudent().getFullName());
        for (Question question: result.getAnsweredQuestions()) {
                Answer correctAnswer = question.getAnswers().stream()
                        .filter(Answer::getIsCorrect).findFirst().get();
            Answer studentsAnswer = result.getAnswers().get(question);
            ioService.printFormattedLine("%s. Correct answer is %s. Student's answer is %s, it is %s",
                    question.getText(), correctAnswer.getText(), studentsAnswer.getText(),
                    correctAnswer.equals(studentsAnswer) ? "correct V" : "incorrect X");

            }

        long totalScore = getTotalScore(result);
        ioService.printFormattedLine("Total score is: %s", totalScore);

        if (totalScore >= testConfig.getNumberRightAnswersToPass()) {
            ioService.printFormattedLine("Congratulations! You passed the test!");
        } else {
            ioService.printFormattedLine("Sorry, you didn't pass the test!");
        }

    }

    public long getTotalScore(TestingResult result) {
        return result.getAnswers().values().stream().filter(Answer::getIsCorrect).count();
    }

}
