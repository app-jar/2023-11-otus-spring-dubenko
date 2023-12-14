package ru.otus.hw.service.student;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.hw.service.io.InputService;

@Configuration
public class StudentConfiguration {

    @Bean
    public StudentService getStudentService(InputService inputService) {
        return new StudentServiceImpl(inputService);
    }
}
