package ru.otus.hw.dto.mapper;

import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.models.Genre;

public class GenreMapper {

    public static GenreDto toDto(Genre genre) {
        return new GenreDto(genre.getId(), genre.getName());
    }

    public static Genre toModel(GenreDto genre) {
        return new Genre().setId(genre.getId()).setName(genre.getName());
    }
}
