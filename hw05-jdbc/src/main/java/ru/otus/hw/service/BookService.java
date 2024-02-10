package ru.otus.hw.service;

import ru.otus.hw.domain.Book;

import java.util.List;

public interface BookService {
    List<Book> all();

    Book get(Long id);

    List<Book> search(String nameQuery, String authorNameQuery);

    Book rename(Long id, String newName);

    Book setAuthorId(Long id, Long newAuthorId);

    Book save(Book book);

    Book create(String name, Long authorId, List<Long> categories);

    void delete(Long id);
}
