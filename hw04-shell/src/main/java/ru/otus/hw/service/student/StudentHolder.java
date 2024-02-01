package ru.otus.hw.service.student;

import ru.otus.hw.domain.Student;

public interface StudentHolder {
    Student getStudent();

    void setStudent(Student student);

    default boolean isStudentDetermined() {
        return null != getStudent();
    }
}
