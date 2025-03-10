package ru.otus.hw.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.GenreDto;

@RequiredArgsConstructor
@Component
public class BookConverter {
    private final AuthorConverter authorConverter;

    public String bookToString(BookDto book) {
        var genresString = String.join(", ", book.genres().stream()
                .map(GenreDto::name)
                .toList()
        );
        return "Id: %s, title: %s, author: {%s}, genres: [%s]".formatted(
                book.id(),
                book.title(),
                authorConverter.authorToString(book.author()),
                genresString);
    }
}
