package ru.otus.hw.services.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.mapper.BookMapper;
import ru.otus.hw.exceptions.BadRequestException;
import ru.otus.hw.exceptions.InternalServerException;
import ru.otus.hw.exceptions.NotFoundException;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.GenreRepository;
import ru.otus.hw.repositories.specifications.BookSpecification;
import ru.otus.hw.services.BookService;

import java.util.List;
import java.util.Optional;

import static org.springframework.util.CollectionUtils.isEmpty;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<BookDto> findById(long id) {
        return bookRepository.findById(id)
                .map(BookMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
                .map(BookMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> findAllByTitleQuery(String titleQuery) {
        return bookRepository.findAll(BookSpecification.titleQuery(titleQuery))
                .stream()
                .map(BookMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public BookDto insert(BookDto book) {
        return save(BookMapper.toModel(book));
    }

    @Override
    @Transactional
    public BookDto update(BookDto bookDto) {

        bookRepository.findById(bookDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("book %d is not found"
                        .formatted(bookDto.getId())));

        return save(BookMapper.toModel(bookDto));
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }

    private BookDto save(Book book) {
        if (isEmpty(book.getGenres())) {
            throw new IllegalArgumentException("Genres must not be null");
        }

        final var author = authorRepository.findById(book.getAuthor().getId())
                .orElseThrow(() -> new EntityNotFoundException("Author is not found"));
        final var genres = genreRepository.findAllById(
                book.getGenres().stream()
                        .map(Genre::getId)
                        .toList()
        );

        book.setGenres(genres).setAuthor(author);

        return BookMapper.toDto(bookRepository.save(book));
    }
}
