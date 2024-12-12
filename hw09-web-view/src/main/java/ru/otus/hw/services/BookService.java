package ru.otus.hw.services;

import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookEditDto;

import java.util.List;
import java.util.Optional;

public interface BookService {
    Optional<BookEditDto> findById(long id);

    List<BookDto> findAll();

    List<BookDto> findAllByTitleQuery(String titleQuery);

    BookDto insert(BookEditDto book);

    BookDto update(BookEditDto book);

    void deleteById(long id);
}
