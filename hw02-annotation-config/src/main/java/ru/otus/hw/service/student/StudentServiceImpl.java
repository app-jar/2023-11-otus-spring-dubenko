package ru.otus.hw.service.student;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.domain.Student;
import ru.otus.hw.service.io.InputService;

@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final InputService inputService;

    @Override
    public Student determineCurrentStudent() {
        var firstName = inputService.readStringWithPrompt("Please input your first name");
        var lastName = inputService.readStringWithPrompt("Please input your last name");
        return new Student(firstName, lastName);
    }
}
