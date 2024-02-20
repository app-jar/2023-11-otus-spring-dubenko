package ru.otus.hw;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import ru.otus.hw.service.test.TestRunnerService;


@PropertySource("classpath:application.properties")
@ComponentScan
public class Application {
    public static void main(String[] args) {
        final var context = new AnnotationConfigApplicationContext(Application.class);
        final var testRunnerService = context.getBean(TestRunnerService.class);
        testRunnerService.run();
    }
}