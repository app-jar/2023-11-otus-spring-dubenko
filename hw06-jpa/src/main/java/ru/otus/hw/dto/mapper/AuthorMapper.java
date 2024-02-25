package ru.otus.hw.dto.mapper;

import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.models.Author;

public class AuthorMapper {

    public static AuthorDto toDto(Author author) {
        return new AuthorDto(author.getId(), author.getFullName());
    }

    public static Author toModel(AuthorDto author) {
        return new Author().setId(author.id()).setFullName(author.fullName());
    }
}
