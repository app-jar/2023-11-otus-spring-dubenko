package ru.otus.hw.service.student;

import ru.otus.hw.domain.Student;

public interface StudentService {

    Student determineCurrentStudent();

    Student determineCurrentStudent(String firstName, String lastName);
}
