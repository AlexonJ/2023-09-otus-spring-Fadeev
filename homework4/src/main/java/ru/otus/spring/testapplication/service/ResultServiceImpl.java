package ru.otus.spring.testapplication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.testapplication.config.TestConfig;
import ru.otus.spring.testapplication.domain.Answer;
import ru.otus.spring.testapplication.domain.Question;
import ru.otus.spring.testapplication.domain.TestingResult;

@RequiredArgsConstructor
@Service
public class ResultServiceImpl implements ResultService {

    private final TestConfig testConfig;

    private final StreamsIOService ioService;

    private final MessageService messageService;

    @Override
    public void showResults(TestingResult result) {
        ioService.printLine("");
        ioService.printFormattedLine(messageService.getMessage("report.header",
                new String[]{result.getStudent().getFullName()}));
        for (Question question: result.getAnsweredQuestions()) {
                Answer correctAnswer = question.getAnswers().stream()
                        .filter(Answer::getIsCorrect).findFirst().orElseThrow();
            Answer studentsAnswer = result.getAnswers().get(question);
            ioService.printFormattedLine(messageService.getMessage("report.question-line", new String[]{
                    question.getText(), correctAnswer.getText(), studentsAnswer.getText(),
                    correctAnswer.equals(studentsAnswer) ?
                            messageService.getMessage("report.answer-correct") :
                            messageService.getMessage("report.answer-incorrect")}));
            }
        long totalScore = getTotalScore(result);
        ioService.printFormattedLine(messageService.getMessage("report.total-score",
                new String[]{Long.toString(totalScore)}));

        if (totalScore >= testConfig.getNumberRightAnswersToPass()) {
            ioService.printFormattedLine(messageService.getMessage("report.pass-message"));
        } else {
            ioService.printFormattedLine(messageService.getMessage("report.fail-message"));
        }
    }

    public long getTotalScore(TestingResult result) {
        return result.getAnswers().values().stream().filter(Answer::getIsCorrect).count();
    }

}
