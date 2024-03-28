package ru.otus.hw.dto.mapper;

import ru.otus.hw.dto.BookDto;
import ru.otus.hw.models.Book;


public class BookMapper {

    public static BookDto toDto(Book book) {
        return new BookDto(
                book.getId(),
                book.getTitle(),
                AuthorMapper.toDto(book.getAuthor()),
                book.getGenres()
        );
    }

    public static Book toModel(BookDto book) {
        return new Book()
                .setId(book.id())
                .setTitle(book.title())
                .setAuthor(AuthorMapper.toModel(book.author()))
                .setGenres(book.genres());
    }
}
