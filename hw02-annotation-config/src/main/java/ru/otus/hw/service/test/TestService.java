package ru.otus.hw.service.test;

import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

public interface TestService {
    TestResult executeTestFor(Student student);
}
