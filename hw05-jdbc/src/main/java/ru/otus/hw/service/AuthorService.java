package ru.otus.hw.service;

import ru.otus.hw.domain.Author;

import java.util.List;

public interface AuthorService {
    List<Author> all();

    Author get(Long id);

    Author rename(Long id, String newName);

    Author create(String name);

    void delete(Long id);
}
