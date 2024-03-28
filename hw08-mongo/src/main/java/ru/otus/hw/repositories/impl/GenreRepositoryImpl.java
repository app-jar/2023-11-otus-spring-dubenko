package ru.otus.hw.repositories.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Book;
import ru.otus.hw.repositories.GenreRepository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class GenreRepositoryImpl implements GenreRepository {

    private final MongoOperations operations;

    @Override
    public List<String> findAll() {
        return operations.findDistinct("genres", Book.class, String.class);
    }
}
