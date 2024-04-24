package ru.otus.hw.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.dto.CommentDto;

@RequiredArgsConstructor
@Component
public class CommentConverter {
    public String commnetToString(CommentDto comment) {
        return "Id: %d, book: %s, text: %s".formatted(
                comment.getId(),
                comment.getBookTitle(),
                comment.getText()
        );
    }
}