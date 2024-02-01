package ru.otus.hw.service.test;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.exceptions.AnonymousException;
import ru.otus.hw.service.io.i18n.MessageService;
import ru.otus.hw.service.student.StudentHolder;
import ru.otus.hw.service.student.StudentService;

@Service
@RequiredArgsConstructor
public class TestRunnerService {

    private final StudentHolder studentHolder;

    private final TestService testService;

    private final StudentService studentService;

    private final ResultService resultService;

    private final MessageService messages;

    public void determineStudent(String firstName, String lastName) {
        studentHolder.setStudent(studentService.determineCurrentStudent(firstName, lastName));
    }

    public void runTest() {
        if (studentHolder.isStudentDetermined()) {
            throw new AnonymousException(messages.get("output.anonymous"));
        }
        final var testResult = testService.executeTest();
        resultService.showResult(testResult);
    }

    public void exit() {
        studentHolder.setStudent(null);
    }

}
