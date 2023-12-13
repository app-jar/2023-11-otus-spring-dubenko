package ru.otus.hw.domain;

import java.util.List;


public record TestResult (Student student, List<AnswerResult> questionResults) { }
