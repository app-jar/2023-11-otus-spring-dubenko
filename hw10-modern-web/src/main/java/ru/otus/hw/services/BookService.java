package ru.otus.hw.services;

import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookUpdateDto;

import java.util.List;
import java.util.Optional;

public interface BookService {
    Optional<BookDto> findById(long id);

    List<BookDto> findAll();

    List<BookDto> findAllByTitleQuery(String titleQuery);

    BookDto insert(BookCreateDto book);

    BookDto update(BookUpdateDto book);

    void deleteById(long id);
}
