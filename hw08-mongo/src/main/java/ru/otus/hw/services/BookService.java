package ru.otus.hw.services;

import ru.otus.hw.dto.BookDto;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BookService {
    Optional<BookDto> findById(long id);

    List<BookDto> findAll();

    List<BookDto> findAllByTitleQuery(String titleQuery);

    BookDto insert(String title, long authorId, Set<String> genres);

    BookDto update(long id, String title, long authorId, Set<String> genres);

    void deleteById(long id);
}
