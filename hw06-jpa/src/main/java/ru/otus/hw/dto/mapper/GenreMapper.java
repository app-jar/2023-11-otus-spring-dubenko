package ru.otus.hw.dto.mapper;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Genre;

public class GenreMapper {

    public static GenreDto toDto(Genre genre) {
        return new GenreDto(genre.getId(), genre.getName());
    }

    public static Genre toModel(GenreDto genre) {
        return new Genre().setId(genre.id()).setName(genre.name());
    }
}
