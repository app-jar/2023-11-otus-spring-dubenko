package ru.otus.hw.service.test;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.config.TestConfig;
import ru.otus.hw.domain.AnswerResult;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.TestResult;
import ru.otus.hw.service.io.OutputService;

import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService {

    private final TestConfig testConfig;

    private final OutputService outputService;

    @Override
    public void showResult(TestResult testResult) {
        final var rightAnsweredCount = getRightAnswersCount(testResult);
        final var questionCount = testResult.questionResults().size();

        outputService.printLine("");
        outputService.printLine("Test results: ");
        outputService.printFormattedLine("Student: %s", testResult.student().getFullName());
        outputService.printFormattedLine("Answered questions count: %d", questionCount);
        outputService.printFormattedLine("Right answers count: %d", rightAnsweredCount);

        if (rightAnsweredCount >= testConfig.getRightAnswersCountToPass()) {
            outputService.printLine("Congratulations! You passed test!");
        } else {
            outputService.printLine("Sorry. You fail test.");
        }

        if (testConfig.isShowErrors() && rightAnsweredCount < questionCount) {
            showErrors(testResult);
        }
    }

    private void showErrors(TestResult testResult) {
        outputService.printLine("");
        outputService.printLine("You have mistake at this questions:");

        testResult.questionResults().stream()
                .filter(Predicate.not(AnswerResult::isCorrect))
                .map(AnswerResult::question)
                .map(Question::text)
                .forEach(outputService::printLine);
    }

    private long getRightAnswersCount(TestResult testResult) {
        return testResult.questionResults().stream()
                .filter(AnswerResult::isCorrect)
                .count();
    }
}
