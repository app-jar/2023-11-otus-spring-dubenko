package ru.otus.hw;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.hw.service.test.TestRunnerService;

@ComponentScan
public class Application {
    public static void main(String[] args) {
        final var context = new AnnotationConfigApplicationContext(Application.class);;
        final var testRunnerService = context.getBean(TestRunnerService.class);
        testRunnerService.run();
    }
}