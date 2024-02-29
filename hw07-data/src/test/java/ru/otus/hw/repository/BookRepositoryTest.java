package ru.otus.hw.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.specifications.BookSpecification;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий книг ")
@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository repo;

    @Test
    @DisplayName("фильтрует книги по автору")
    void filterByAuthor() {
        final var expected1 = "Александр Пушкин";
        final var expected2 = "Алексей Толстой";

        final var actual = repo.findAllByAuthorFullNameContains("лекс").stream()
                .map(Book::getAuthor)
                .map(Author::getFullName)
                .toList();

        assertThat(actual).containsExactlyInAnyOrderElementsOf(List.of(
                expected1,
                expected2
        ));
    }

    @Test
    @DisplayName("фильтрует книги по названию")
    void filterByTitle() {
        final var expected = "Кавказский Пленник";

        final var actual = repo.findAll(BookSpecification.titleQuery("авказ")).stream()
                .map(Book::getTitle)
                .toList();

        assertThat(actual).containsExactlyInAnyOrderElementsOf(List.of(
                expected,
                expected
        ));
    }

}