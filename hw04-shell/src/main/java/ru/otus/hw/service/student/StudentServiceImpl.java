package ru.otus.hw.service.student;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.domain.Student;
import ru.otus.hw.service.io.i18n.MessageService;
import ru.otus.hw.service.io.InputService;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final InputService inputService;

    private final MessageService messages;

    @Override
    public Student determineCurrentStudent() {
        var firstName = inputService.readStringWithPrompt(messages.get("input.name.first"));
        var lastName = inputService.readStringWithPrompt(messages.get("input.name.last"));
        return this.determineCurrentStudent(firstName, lastName);
    }

    @Override
    public Student determineCurrentStudent(String firstName, String lastName) {
        return new Student(firstName, lastName);
    }
}
