package ru.otus.hw.shell;

import org.jline.utils.AttributedString;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.jline.PromptProvider;

@Configuration
public class ShellConfig implements PromptProvider {

    @Override
    public final AttributedString getPrompt() {
        return new AttributedString(AnsiOutput
                .toString(AnsiColor.BLUE, "book", AnsiColor.DEFAULT, ":>"));
    }

}
