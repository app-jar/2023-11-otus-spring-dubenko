package ru.otus.hw.shell;

import lombok.RequiredArgsConstructor;
import org.jline.utils.AttributedString;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.jline.PromptProvider;
import ru.otus.hw.service.student.StudentHolder;

@Configuration
@RequiredArgsConstructor
public class ShellConfiguration implements PromptProvider {

    private static final String DEFAULT_NAME = "Anonymous student";

    private final StudentHolder studentHolder;

    @Override
    public final AttributedString getPrompt() {
        final var studentName = getStudentName();
        return new AttributedString("test " + studentName + ":>");
    }

    private String getStudentName() {
        final var anonymous = !studentHolder.isStudentDetermined();
        final var name = anonymous ?
                DEFAULT_NAME : studentHolder.getStudent().getFullName();
        final var color = anonymous ? AnsiColor.RED : AnsiColor.GREEN;
        return AnsiOutput.toString(color, name, AnsiColor.DEFAULT);
    }
}
