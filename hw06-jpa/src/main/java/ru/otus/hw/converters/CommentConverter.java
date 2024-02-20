package ru.otus.hw.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.dto.CommentDto;

@RequiredArgsConstructor
@Component
public class CommentConverter {
    public String commnetToString(CommentDto comment) {
        return "Id: %d, text: %s".formatted(
                comment.id(),
                comment.text()
        );
    }
}
