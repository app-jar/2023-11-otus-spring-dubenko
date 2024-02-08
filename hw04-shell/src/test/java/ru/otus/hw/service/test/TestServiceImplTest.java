package ru.otus.hw.service.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.hw.domain.TestResult;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.otus.hw.service.test.TestConstants.QUESTION_RESULTS;
import static ru.otus.hw.service.test.TestConstants.STUDENT;

@DisplayName("Реализация TestService")
@SpringBootTest(classes = TestConfig.class)
class TestServiceImplTest {

    @Autowired
    private TestService service;

    @Test
    @DisplayName("правильно строит результат")
    void rightResult() {
        final var testResult = service.executeTestFor(STUDENT);

        assertThat(testResult)
                .isEqualTo(new TestResult(STUDENT, QUESTION_RESULTS));
    }

}
