package ru.otus.hw.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Реализация QuestionDao для CSV файлов")
class CsvQuestionDaoTest {
    @Mock
    private TestFileNameProvider mockFileNameProvider;

    private QuestionDao dao;

    @BeforeEach
    void setUp() {
        dao = new CsvQuestionDao(mockFileNameProvider);
    }

    @Test
    @DisplayName("правильно парсит CSV с вопросами")
    void findAll() {
        final var etalon = new Question("question?", List.of(
                new Answer("answer1", false),
                new Answer("answer2", true),
                new Answer("answer3", false),
                new Answer("answer4", false)
        ));
        when(mockFileNameProvider.getTestFileName()).thenReturn("questionsTest.csv");
        final var result = dao.findAll();
        assertThat(result).containsOnly(etalon);
    }

    @Test
    @DisplayName("падает, если CSV-файл содержит ошибку")
    void errorIfNotCorrectFile() {
        when(mockFileNameProvider.getTestFileName()).thenReturn("questionsErrorTest.csv");
        assertThrows(QuestionReadException.class, () -> dao.findAll());
    }
}
