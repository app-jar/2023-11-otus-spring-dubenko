package ru.otus.hw.dao;

import ru.otus.hw.domain.Author;

import java.util.List;

public interface AuthorDao {

    List<Author> all();

    Author byId(Long id);

    void deleteById(Long id);

    Author save(Author author);
}
