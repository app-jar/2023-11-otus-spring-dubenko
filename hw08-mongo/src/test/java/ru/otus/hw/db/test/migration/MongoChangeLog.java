package ru.otus.hw.db.test.migration;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import com.mongodb.client.MongoDatabase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;

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
    public void fillDatabase(MongockTemplate mongoTemplate) {
        final var genres = getGenries();
        final var authors = getAuthors();
        final var books = getBooks();
        final var comments = getComments(books);

        associate(authors.get(0), books.get(0));
        associate(authors.get(1), books.get(1));
        associate(authors.get(2), books.get(2));

        genres.forEach(mongoTemplate::insert);
        books.forEach(mongoTemplate::insert);
        authors.forEach(mongoTemplate::insert);
        comments.forEach(mongoTemplate::insert);
    }

    private List<Comment> getComments(List<Book> books) {
        return List.of(
                new Comment()
                        .setBook(books.get(0))
                        .setText("Comment_1"),
                new Comment()
                        .setBook(books.get(0))
                        .setText("Comment_2"),
                new Comment()
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
        book.setAuthor(author);
    }

    private Author createAuthor(Integer number) {
        return new Author()
                .setFullName("Author_" + number);
    }

    private Book createBook(Integer number, Integer... genres) {
        return new Book()
                .setTitle("Book_" + number)
                .setGenres(Stream.of(genres)
                        .map(this::createGenre)
                        .toList()
                );
    }

    private Genre createGenre(Integer number) {
        return new Genre()
                .setName("Genre_" + number);
    }
    private List<Genre> getGenries() {
        return Stream.of(1, 2, 3, 4, 5, 6)
                .map(this::createGenre)
                .toList();
    }
}
