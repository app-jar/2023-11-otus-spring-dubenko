package ru.otus.hw.db.migration;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@ChangeLog
@Component
@RequiredArgsConstructor
public class MongoChangeLog {

    @ChangeSet(order = "001", systemVersion = "1", id = "seedDatabase", author = "ilia.dubenko")
    public void seedDatabase(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", systemVersion = "1", id = "fillDatabase", author = "ilia.dubenko")
    public void fillDatabase(BookRepository bookRepo, AuthorRepository authorRepo, CommentRepository commentRepo) {
        final var authors = getAuthors();
        final var books = getBooks();
        final var comments = getComments(books);

        associate(authors.get(0), books.get(0));
        associate(authors.get(1), books.get(1));
        associate(authors.get(2), books.get(2));

        bookRepo.saveAll(books);
        authorRepo.saveAll(authors);
        commentRepo.saveAll(comments);
    }

    private List<Comment> getComments(List<Book> books) {
        return List.of(
                new Comment()
                        .setId(1L)
                        .setBook(books.get(0))
                        .setText("Comment_1"),
                new Comment()
                        .setId(2L)
                        .setBook(books.get(0))
                        .setText("Comment_2"),
                new Comment()
                        .setId(3L)
                        .setBook(books.get(1))
                        .setText("Comment_3")
        );
    }

    private List<Book> getBooks() {
        return List.of(
                createBook(1, 1, 2),
                createBook(2, 3, 4),
                createBook(3, 5, 6)
        );
    }

    private List<Author> getAuthors() {
        return List.of(
                createAuthor(1),
                createAuthor(2),
                createAuthor(3)
        );
    }

    private void associate(Author author, Book book) {
        author.getBooks().add(book);
        book.setAuthor(author);
    }

    private Author createAuthor(Integer number) {
        return new Author()
                .setId(number)
                .setFullName("Author_" + number)
                .setBooks(new ArrayList<>());
    }

    private Book createBook(Integer number, Integer... genres) {
        return new Book()
                .setId(number.longValue())
                .setTitle("Book_" + number)
                .setGenres(Stream.of(genres)
                        .map(x -> "Genre_" + x)
                        .toList()
                );
    }
}