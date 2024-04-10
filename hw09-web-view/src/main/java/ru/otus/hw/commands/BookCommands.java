package ru.otus.hw.commands;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.converters.BookConverter;
import ru.otus.hw.services.BookService;

import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings({"SpellCheckingInspection", "unused"})
@RequiredArgsConstructor

public class BookCommands {

    private final BookService bookService;

    private final BookConverter bookConverter;

    public String findAllBooks() {
        return bookService.findAll().stream()
                .map(bookConverter::bookToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    public String findBookById(long id) {
        return bookService.findById(id)
                .map(bookConverter::bookToString)
                .orElse("Book with id %d not found".formatted(id));
    }

    public String findBookByTitleQuery(String authorQuery) {
        return bookService.findAllByTitleQuery(authorQuery)
                .stream()
                .map(bookConverter::bookToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    // bins newBook 1 1,6
    public String insertBook(String title, long authorId, Set<Long> genresIds) {
        var savedBook = bookService.insert(title, authorId, genresIds);
        return bookConverter.bookToString(savedBook);
    }

    // bupd 4 editedBook 3 2,5
    public String updateBook(long id, String title, long authorId,   Set<Long> genresIds) {
        var savedBook = bookService.update(id, title, authorId, genresIds);
        return bookConverter.bookToString(savedBook);
    }

    // bdel 4
    public void deleteBook(long id) {
        bookService.deleteById(id);
    }
}
