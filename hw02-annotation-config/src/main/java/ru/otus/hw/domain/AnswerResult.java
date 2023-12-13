package ru.otus.hw.domain;

public record AnswerResult(Question question, int answerNumber, boolean isCorrect) {
}
