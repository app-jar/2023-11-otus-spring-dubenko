package ru.otus.hw.service.test;

import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.QuestionResult;
import ru.otus.hw.domain.Student;

import java.util.ArrayList;
import java.util.List;

public class TestConstants {

    public static final int REGULAR_ANSWER = 0;
    public static final Student STUDENT = new Student("Name", "Surname");
    public static final List<QuestionResult> QUESTION_RESULTS = List.of(
            createQuestionResult("Q0", 0, "a00", "a01", "a02"),
            createQuestionResult("Q1", 1, "a10", "a11", "a12"),
            createQuestionResult("Q2", 2, "a20", "a21", "a22"),
            createQuestionResult("Q3", 3, "a30", "a31", "a32", "a33"),
            createQuestionResult("Q4", 0, "a40", "a41", "a42", "a43")
    );

    private static QuestionResult createQuestionResult(String txt, int correctNumber, String... answerTexts) {
        final var answers = new ArrayList<Answer>();
        for (var i = 0; i < answerTexts.length; i++) {
            answers.add(new Answer(answerTexts[i], i == correctNumber));
        }

        final var question = new Question(txt, answers);
        final var isCorrect = REGULAR_ANSWER == correctNumber;

        return new QuestionResult(question, REGULAR_ANSWER, isCorrect);
    }
}
