package ru.otus.hw.dto.mapper;

import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookEditDto;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

public class BookMapper {

    public static BookEditDto toEditDto(Book book) {
        return new BookEditDto(
                book.getId(),
                book.getTitle(),
                book.getAuthor().getId(),
                book.getGenres().stream().map(Genre::getId).toList()
        );
    }

    public static Book toModel(BookEditDto book) {
        return new Book()
                .setId(book.getId())
                .setTitle(book.getTitle())
                .setAuthor(new Author().setId(book.getAuthorId()))
                .setGenres(book.getGenreIds().stream()
                        .map(id -> new Genre().setId(id))
                        .toList()
                );
    }

    public static BookDto toDto(Book book) {
        return new BookDto(
                book.getId(),
                book.getTitle(),
                AuthorMapper.toDto(book.getAuthor()),
                book.getGenres().stream().map(GenreMapper::toDto).toList()
        );
    }
}
