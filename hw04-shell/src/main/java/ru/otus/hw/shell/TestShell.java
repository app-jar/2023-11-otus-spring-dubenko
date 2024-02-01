package ru.otus.hw.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.properties.MessageProperties;
import ru.otus.hw.service.io.i18n.MessageIOService;
import ru.otus.hw.service.student.StudentHolder;
import ru.otus.hw.service.test.TestRunnerService;

@ShellComponent
@RequiredArgsConstructor
public class TestShell {

    private final StudentHolder studentHolder;

    private final TestRunnerService testService;

    private final MessageIOService ioService;

    private final MessageProperties msgConf;

    @ShellMethod(value = "Установка локали", key = {"locale", "lang"})
    public String identify(String locale) {
        msgConf.setLocale(locale);
        ioService.printMessage("output.locale.setted", msgConf.getLocale());
        return "";
    }

    @ShellMethod(value = "Идентификация студента", key = {"i", "id", "identify", "im"})
    public String identify(String firstName, String lastName) {
        testService.determineStudent(firstName, lastName);
        ioService.printMessage("output.name", studentHolder.getStudent().getFullName());
        return "";
    }

    @ShellMethod(value = "Сброс студента", key = {"logout", "out"})
    public String exit() {
        testService.exit();
        return "";
    }

    @ShellMethod(value = "Пройти тест", key = {"s", "test", "start"})
    public String startTest() {
        testService.runTest();
        return "";
    }

}
