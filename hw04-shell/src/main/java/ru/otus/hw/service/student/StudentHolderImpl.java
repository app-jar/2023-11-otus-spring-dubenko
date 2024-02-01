package ru.otus.hw.service.student;

import lombok.Data;
import org.springframework.stereotype.Component;
import ru.otus.hw.domain.Student;

@Component
@Data
public class StudentHolderImpl implements StudentHolder {

    private Student student;

}
