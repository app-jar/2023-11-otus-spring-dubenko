package ru.otus.hw.dto.mapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;


public class BookMapper {

    public static BookDto toDto(Book book) {
        return new BookDto(
                book.getId(),
                book.getTitle(),
                AuthorMapper.toDto(book.getAuthor()),
                book.getGenres().stream().map(GenreMapper::toDto).toList()
        );
    }

    public static Book toModel(BookDto book) {
        return new Book()
                .setId(book.id())
                .setTitle(book.title())
                .setAuthor(AuthorMapper.toModel(book.author()))
                .setGenres(book.genres().stream().map(GenreMapper::toModel).toList());
    }
}
