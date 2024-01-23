package ru.otus.hw.service.test;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;
import ru.otus.hw.service.student.StudentService;

@Service
@RequiredArgsConstructor
public class TestRunnerService implements ApplicationRunner {

    private final TestService testService;

    private final StudentService studentService;

    private final ResultService resultService;

    @Override
    public void run(ApplicationArguments args) {
        var student = studentService.determineCurrentStudent();
        var testResult = testService.executeTestFor(student);
        resultService.showResult(testResult);
    }
}
