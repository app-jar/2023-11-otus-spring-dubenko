package ru.otus.hw.db.migration;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;
import ru.otus.hw.repositories.GenreRepository;

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
    public void fillDatabase(BookRepository bookRepo, AuthorRepository authorRepo,
                             CommentRepository commentRepo, GenreRepository genreRepo) {
        final var genries = getGenries();
        final var authors = getAuthors();
        final var books = getBooks(genries);
        final var comments = getComments(books);

        associate(authors.get(0), books.get(0));
        associate(authors.get(1), books.get(1));
        associate(authors.get(2), books.get(2));

        genreRepo.saveAll(genries);
        bookRepo.saveAll(books);
        authorRepo.saveAll(authors);
        commentRepo.saveAll(comments);
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

    private List<Book> getBooks(List<Genre> genres) {
        return List.of(
                createBook(1, genres.get(0), genres.get(1)),
                createBook(2, genres.get(2), genres.get(3)),
                createBook(3, genres.get(4), genres.get(5))
        );
    }

    private List<Author> getAuthors() {
        return List.of(
                createAuthor(1),
                createAuthor(2),
                createAuthor(3)
        );
    }

    private List<Genre> getGenries() {
        return Stream.of(1, 2, 3, 4, 5, 6)
                .map(this::createGenre)
                .toList();
    }

    private void associate(Author author, Book book) {

        book.setAuthor(author);
    }

    private Author createAuthor(Integer number) {
        return new Author()
                .setFullName("Author_" + number);
    }

    private Book createBook(Integer number, Genre... genres) {
        return new Book()
                .setTitle("Book_" + number)
                .setGenres(List.of(genres));
    }



    private Genre createGenre(Integer number) {
        return new Genre()
                .setName("Genre_" + number);
    }
}
