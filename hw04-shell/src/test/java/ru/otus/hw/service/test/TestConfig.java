package ru.otus.hw.service.test;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.QuestionResult;
import ru.otus.hw.service.io.IOService;
import ru.otus.hw.service.io.i18n.MessageService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static ru.otus.hw.service.test.TestConstants.QUESTION_RESULTS;
import static ru.otus.hw.service.test.TestConstants.REGULAR_ANSWER;
import static ru.otus.hw.service.test.TestServiceImpl.MIN_ANSWER_NUMBER;

@TestConfiguration
class TestConfig {
    @MockBean
    private IOService mockIO;
    @MockBean
    private QuestionDao questionDao;

    @MockBean
    private MessageService messageService;

    @PostConstruct
    public void setUpMocks() {
        final var questions = QUESTION_RESULTS.stream().map(QuestionResult::question).toList();
        final var regularInput = REGULAR_ANSWER + MIN_ANSWER_NUMBER;
        when(questionDao.findAll()).thenReturn(questions);
        when(mockIO.readIntForRange(anyInt(), anyInt(), any())).thenReturn(regularInput);
    }
}
