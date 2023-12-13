package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.List;

@RequiredArgsConstructor
public class NumberQuestionPrintService implements QuestionPrintService {
    private final IOService ioService;

    @Override
    public void print(List<Question> questions) {
        for (int number = 0; number < questions.size(); number++) {
            this.printQuestion(number, questions.get(number));
        }

    }

    private void printQuestion(int questionNumber, Question question) {
        ioService.printFormattedLine("%03d. %s", questionNumber + 1, question.text());
        for (int answerNumber = 0; answerNumber < question.answers().size(); answerNumber++) {
            this.printAnswer(answerNumber, question.answers().get(answerNumber));
        }

        ioService.printLine("");
    }

    private void printAnswer(int answerNumber, Answer answer) {
        ioService.printFormattedLine("    %d) %s", answerNumber + 1, answer.text());
    }
}
