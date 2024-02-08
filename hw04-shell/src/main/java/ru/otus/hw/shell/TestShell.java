package ru.otus.hw.shell;

import lombok.RequiredArgsConstructor;
import org.jline.utils.AttributedString;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.shell.Availability;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import ru.otus.hw.domain.Student;
import ru.otus.hw.properties.MessageProperties;
import ru.otus.hw.service.io.i18n.MessageIOService;
import ru.otus.hw.service.io.i18n.MessageService;
import ru.otus.hw.service.student.StudentService;
import ru.otus.hw.service.test.TestRunnerService;

@ShellComponent
@RequiredArgsConstructor
public class TestShell implements PromptProvider {

    private static final String DEFAULT_NAME = "Anonymous student";

    private Student student = null;

    private final MessageService messages;

    private final TestRunnerService testService;

    private final MessageIOService ioService;

    private final MessageProperties msgConf;

    private final StudentService studentService;

    @ShellMethod(value = "Установка локали", key = {"locale", "lang"})
    public String identify(String locale) {
        msgConf.setLocale(locale);
        ioService.printMessage("output.locale.setted", msgConf.getLocale());
        return "";
    }

    @ShellMethod(value = "Идентификация студента", key = {"i", "id", "identify", "im"})
    public String identify(String firstName, String lastName) {
        student = studentService.determineCurrentStudent(firstName, lastName);
        return messages.get("output.name", student.getFullName());
    }

    @ShellMethod(value = "Сброс студента", key = {"logout", "out"})
    public String exit() {
        student = null;
        return "";
    }

    @ShellMethod(value = "Пройти тест", key = {"s", "test", "start"})
    @ShellMethodAvailability("isTestAvailability")
    public String startTest() {
        testService.runTestFor(student);
        return "";
    }

    public Availability isTestAvailability() {
        return isStudentDetermined() ? Availability.available()
                : Availability.unavailable(messages.get("output.anonymous"));
    }

    @Override
    public final AttributedString getPrompt() {
        final var studentName = getStudentName();
        return new AttributedString("test " + studentName + ":>");
    }

    private String getStudentName() {
        final var anonymous = !isStudentDetermined();
        final var name = anonymous ?
                DEFAULT_NAME : student.getFullName();
        final var color = anonymous ? AnsiColor.RED : AnsiColor.GREEN;
        return AnsiOutput.toString(color, name, AnsiColor.DEFAULT);
    }

    private boolean isStudentDetermined() {
        return null != student;
    }
}
