package ru.otus.hw.repository.old;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.GenreRepository;

import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий жанров")
@DataJpaTest(properties = "spring.sql.init.data-locations=data_old.sql")
class DataGenreRepositoryTest {

    @Autowired
    private GenreRepository repo;

    @Autowired
    private TestEntityManager em;

    @DisplayName("загружает жанры по id")
    @Test
    void genresByIds() {
        final var expected = TestUtils.getDbGenres().stream()
                .limit(2L)
                .map(Genre::getId)
                .map(id -> em.find(Genre.class, id))
                .toList();
        final var actual = repo.findAllById(expected.stream().map(Genre::getId).collect(Collectors.toSet()));
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

    @DisplayName("загружает список всех жанров")
    @Test
    void genresList() {
        final var actual = repo.findAll();
        final var expected = TestUtils.getDbGenres().stream()
                .map(Genre::getId)
                .map(id -> em.find(Genre.class, id))
                .toList();
        assertThat(actual).containsExactlyElementsOf(expected);
        actual.forEach(System.out::println);
    }


}