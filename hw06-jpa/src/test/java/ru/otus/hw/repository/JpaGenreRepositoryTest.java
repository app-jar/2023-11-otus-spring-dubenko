package ru.otus.hw.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.jpa.JpaGenreRepository;

import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий жанров")
@DataJpaTest
@Import({JpaGenreRepository.class})
class JpaGenreRepositoryTest {

    @Autowired
    private JpaGenreRepository repo;

    @DisplayName("загружает жанры по id")
    @Test
    void genresByIds() {
        final var expected = TestUtils.getDbGenres()
                .stream().limit(2L).toList();
        final var actual = repo.findAllByIds(expected.stream().map(Genre::getId).collect(Collectors.toSet()));
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

    @DisplayName("загружает список всех жанров")
    @Test
    void genresList() {
        final var actual = repo.findAll();
        final var expected = TestUtils.getDbGenres();

        assertThat(actual).containsExactlyElementsOf(expected);
        actual.forEach(System.out::println);
    }


}