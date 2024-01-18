package ru.otus.hw.service.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.CsvQuestionDao;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.*;
import ru.otus.hw.exceptions.QuestionReadException;
import ru.otus.hw.i18n.MessageService;
import ru.otus.hw.service.io.IOService;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static ru.otus.hw.service.test.TestServiceImpl.MIN_ANSWER_NUMBER;

@ExtendWith(MockitoExtension.class)
@DisplayName("Реализация TestService")
class TestServiceImplTest {
    private static final int REGULAR_ANSWER = 0;
    private static final Student STUDENT = new Student("Name", "Surname");

    private static final List<QuestionResult> QUESTION_RESULTS = List.of(
            createQuestionResult("Q0", 0, "a00", "a01", "a02"),
            createQuestionResult("Q1", 1, "a10", "a11", "a12"),
            createQuestionResult("Q2", 2, "a20", "a21", "a22"),
            createQuestionResult("Q3", 3, "a30", "a31", "a32", "a33"),
            createQuestionResult("Q4", 0, "a40", "a41", "a42", "a43")
    );

    @Mock
    private IOService mockIO;

    @Mock
    private QuestionDao mockQuestionDao;

    @Mock
    private MessageService messages;

    private TestServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new TestServiceImpl(mockIO, mockQuestionDao, messages);
    }

    @Test
    @DisplayName("правильно строит результат")
    void rightResult() {
        final var questions = QUESTION_RESULTS.stream().map(QuestionResult::question).toList();
        final var regularInput = REGULAR_ANSWER + MIN_ANSWER_NUMBER;

        when(mockQuestionDao.findAll()).thenReturn(questions);
        when(mockIO.readIntForRange(anyInt(), anyInt(), anyString())).thenReturn(regularInput);
        when(messages.get(anyString(), any(Object[].class))).thenReturn("");
        when(messages.get(anyString())).thenReturn("");

        final var testResult = service.executeTestFor(STUDENT);

        assertThat(testResult)
                .isEqualTo(new TestResult(STUDENT, QUESTION_RESULTS));
    }

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
