package ru.otus.hw.services.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.dto.mapper.BookMapper;
import ru.otus.hw.models.Book;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.GenreRepository;
import ru.otus.hw.repositories.specifications.BookSpecification;
import ru.otus.hw.services.BookService;

import java.util.List;
import java.util.Optional;


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
    public BookDto insert(BookCreateDto bookDto) {
        final var book = new Book();

        final var author = authorRepository.findById(bookDto.getAuthorId())
                .orElseThrow(() -> new EntityNotFoundException("Author is not found"));

        final var genres = genreRepository.findAllById(bookDto.getGenreIds());

        book.setTitle(bookDto.getTitle());
        book.setAuthor(author);
        book.setGenres(genres);
        return BookMapper.toDto(bookRepository.save(book));
    }

    @Override
    @Transactional
    public BookDto update(BookUpdateDto bookDto) {

        final var book = bookRepository.findById(bookDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("book %d is not found"
                        .formatted(bookDto.getId())));

        final var author = authorRepository.findById(bookDto.getAuthorId())
                .orElseThrow(() -> new EntityNotFoundException("Author is not found"));

        final var genres = genreRepository.findAllById(bookDto.getGenreIds());

        book.setTitle(bookDto.getTitle());
        book.setAuthor(author);
        book.setGenres(genres);

        return BookMapper.toDto(bookRepository.save(book));
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }

}
