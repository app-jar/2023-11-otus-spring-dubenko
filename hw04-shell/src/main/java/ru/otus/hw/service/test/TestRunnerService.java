package ru.otus.hw.service.test;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.domain.Student;

@Service
@RequiredArgsConstructor
public class TestRunnerService {

    private final TestService testService;

    private final ResultService resultService;

    public void runTestFor(Student student) {
        final var testResult = testService.executeTestFor(student);
        resultService.showResult(testResult);
    }
}
