package ru.otus.hw.services.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.mapper.BookMapper;
import ru.otus.hw.models.Book;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.specifications.BookSpecification;
import ru.otus.hw.repositories.GenreRepository;
import ru.otus.hw.services.BookService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    public BookDto insert(String title, long authorId, Set<Long> genresIds) {
        return save(new Book().setId(0), title, authorId, genresIds);
    }

    @Override
    @Transactional
    public BookDto update(long id, String title, long authorId, Set<Long> genresIds) {
        final var book = bookRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        return save(book, title, authorId, genresIds);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }

    private BookDto save(Book book, String title, long authorId, Set<Long> genresIds) {
        if (isEmpty(genresIds)) {
            throw new IllegalArgumentException("Genres ids must not be null");
        }

        final var author = authorRepository.findById(authorId);
        final var genres = genreRepository.findAllById(genresIds);

        book
                .setTitle(title)
                .setAuthor(author.get())
                .setGenres(genres);
        return BookMapper.toDto(bookRepository.save(book));
    }
}
