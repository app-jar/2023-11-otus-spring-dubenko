package ru.otus.hw.service.test;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.QuestionResult;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;
import ru.otus.hw.service.io.i18n.MessageService;
import ru.otus.hw.service.io.IOService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {
    public static final int MIN_ANSWER_NUMBER = 1;

    private final IOService ioService;

    private final QuestionDao questionDao;

    private final MessageService messages;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printLine(messages.get("input.test.answer"));

        final var questions = questionDao.findAll();
        final var answerResults = askQuestions(questions);
        return new TestResult(student, answerResults);
    }

    private List<QuestionResult> askQuestions(List<Question> questions) {
        final var results = new ArrayList<QuestionResult>();
        for (int number = 0; number < questions.size(); number++) {
            final var questionResult = this.askQuestion(number, questions.get(number));
            results.add(questionResult);
        }
        return results;
    }

    private QuestionResult askQuestion(int questionNumber, Question question) {
        printQuestion(questionNumber, question);

        final var answers = question.answers();
        final var maxNumber = answers.size() - 1;

        final var resultNumber = readAnswer(maxNumber);
        final var isCorrect = answers.get(resultNumber).isCorrect();

        return new QuestionResult(question, resultNumber, isCorrect);
    }

    private int readAnswer(int max) {
        final var shiftedMax = max + MIN_ANSWER_NUMBER;
        final var shiftNumber = ioService.readIntForRange(MIN_ANSWER_NUMBER, shiftedMax,
                messages.get("input.test.error",
                        MIN_ANSWER_NUMBER,
                        shiftedMax
                )
        );

        return shiftNumber - MIN_ANSWER_NUMBER;
    }

    private void printQuestion(int questionNumber, Question question) {
        ioService.printFormattedLine("%03d. %s", questionNumber + 1, question.text());
        for (int answerNumber = 0; answerNumber < question.answers().size(); answerNumber++) {
            this.printAnswer(answerNumber, question.answers().get(answerNumber));
        }

        ioService.printLine("");
    }

    private void printAnswer(int answerNumber, Answer answer) {
        ioService.printFormattedLine("    %d) %s", answerNumber + MIN_ANSWER_NUMBER, answer.text());
    }
}
