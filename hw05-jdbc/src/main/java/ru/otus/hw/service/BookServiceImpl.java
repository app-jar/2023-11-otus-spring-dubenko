package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.BookDao;
import ru.otus.hw.domain.Author;
import ru.otus.hw.domain.Book;
import ru.otus.hw.domain.BookSearch;
import ru.otus.hw.exception.SaveError;
import ru.otus.hw.exception.NotExistsException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final AuthorService authorService;

    private final CategoryService categoryService;

    private final BookDao bookDao;

    @Override
    public List<Book> all() {
        final var books = bookDao.all();
        enrichWithCategories(books);
        return books;
    }

    @Override
    public Book get(Long id) {
        try {
            final var book = bookDao.byId(id);
            enrichWithCategories(book);
            return book;
        } catch (EmptyResultDataAccessException ex) {
            throw new NotExistsException(String.format("Book with id = %s doesn't exist", id));
        }
    }

    @Override
    public List<Book> search(String nameQuery, String authorNameQuery) {
        final var books =  bookDao.search(new BookSearch()
                .nameQuery(nameQuery)
                .authorNameQuery(authorNameQuery)
        );
        enrichWithCategories(books);
        return books;
    }

    @Override
    public Book rename(Long id, String newName) {
        final var book = get(id);
        book.name(newName);
        return save(book);
    }

    @Override
    public Book setAuthorId(Long id, Long newAuthorId) {
        final var book = get(id);
        book.author().id(newAuthorId);
        return save(book);
    }

    @Override
    public Book save(Book book) {
        checkAuthor(book.author().id());
        final var saved =  bookDao.save(book);
        enrichWithCategories(saved);
        return saved;
    }

    @Override
    public Book create(String name, Long authorId, List<Long> categoriesIds) {
        final var book = save(new Book()
                .name(name)
                .author(new Author(authorId, null))
        );
        categoryService.addCategoryToBook(book.id(), categoriesIds);
        return get(book.id());
    }

    @Override
    public void delete(Long id) {
        bookDao.deleteById(id);
    }

    private void checkAuthor(Long id) {
        try {
            authorService.get(id);
        } catch (Exception e) {
            throw new SaveError("Book save error.", e);
        }
    }

    private void enrichWithCategories(List<Book> books) {
        final var mapping = categoryService.bookIdCategoryMapping();
        for (var book : books) {
            book.categories(mapping.getOrDefault(book.id(), List.of()));
        }
    }

    private void enrichWithCategories(Book book) {
        book.categories(categoryService.byBookId(book.id()));
    }
}
