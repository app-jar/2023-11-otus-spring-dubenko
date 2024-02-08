package ru.otus.hw.service.test;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.properties.TestProperties;
import ru.otus.hw.domain.QuestionResult;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.TestResult;
import ru.otus.hw.service.io.i18n.MessageService;
import ru.otus.hw.service.io.OutputService;

import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService {

    private final TestProperties testProperties;

    private final OutputService outputService;

    private final MessageService messages;

    @Override
    public void showResult(TestResult testResult) {
        final var rightAnsweredCount = getRightAnswersCount(testResult);
        final var questionCount = testResult.questionResults().size();

        outputService.printLine("");
        outputService.printLine(messages.get("output.test.result"));
        outputService.printLine(messages.get("output.student", testResult.student().getFullName()));
        outputService.printLine(messages.get("output.count.answered", questionCount));
        outputService.printLine(messages.get("output.count.correct", rightAnsweredCount));

        if (rightAnsweredCount >= testProperties.getRightAnswersCountToPass()) {
            outputService.printLine(messages.get("output.test.passed"));
        } else {
            outputService.printLine(messages.get("output.test.failed"));
        }

        if (testProperties.isShowErrors() && rightAnsweredCount < questionCount) {
            showErrors(testResult);
        }
    }

    private void showErrors(TestResult testResult) {
        outputService.printLine("");
        outputService.printLine(messages.get("output.test.mistake"));

        testResult.questionResults().stream()
                .filter(Predicate.not(QuestionResult::isCorrect))
                .map(QuestionResult::question)
                .map(Question::text)
                .forEach(outputService::printLine);
    }

    private long getRightAnswersCount(TestResult testResult) {
        return testResult.questionResults().stream()
                .filter(QuestionResult::isCorrect)
                .count();
    }
}
