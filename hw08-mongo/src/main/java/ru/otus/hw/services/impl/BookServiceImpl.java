package ru.otus.hw.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.mapper.BookMapper;
import ru.otus.hw.exceptions.NotFoundException;
import ru.otus.hw.models.Book;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
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
        return bookRepository.findAllByTitleContains(titleQuery)
                .stream()
                .map(BookMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public BookDto insert(String title, long authorId, Set<String> genres) {
        return save(new Book().setId(null), title, authorId, genres);
    }

    @Override
    @Transactional
    public BookDto update(long id, String title, long authorId, Set<String> genres) {
        final var book = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book does not exists, id = " + id));
        return save(book, title, authorId, genres);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }

    private BookDto save(Book book, String title, long authorId, Set<String> genres) {
        if (isEmpty(genres)) {
            throw new NotFoundException("Genres ids must not be null");
        }

        final var author = authorRepository.findById(authorId);

        if (author.isEmpty()) {
            throw new NotFoundException("Authors ids must not be null");
        }

        book
                .setTitle(title)
                .setAuthor(author.get())
                .setGenres(genres.stream().toList());
        return BookMapper.toDto(bookRepository.save(book));
    }
}
