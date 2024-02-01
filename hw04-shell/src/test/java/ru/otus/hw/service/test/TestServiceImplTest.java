package ru.otus.hw.service.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.hw.domain.TestResult;
import ru.otus.hw.service.student.StudentHolder;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.otus.hw.service.test.TestConstants.QUESTION_RESULTS;

@DisplayName("Реализация TestService")
@SpringBootTest(classes = TestConfig.class)
class TestServiceImplTest {

    @Autowired
    private TestService service;
    @Autowired
    private StudentHolder studentHolder;

    @Test
    @DisplayName("правильно строит результат")
    void rightResult() {
        final var testResult = service.executeTest();

        assertThat(testResult)
                .isEqualTo(new TestResult(studentHolder.getStudent(), QUESTION_RESULTS));
    }

}
