package ru.otus.hw.repository.old;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Author;
import ru.otus.hw.repositories.jpa.JpaAuthorRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий авторов ")
@DataJpaTest
@Import({JpaAuthorRepository.class})
class JpaAuthorRepositoryTest {

    @Autowired
    private JpaAuthorRepository repo;

    @Autowired
    private TestEntityManager em;

    @DisplayName("загружает автора по id")
    @ParameterizedTest
    @MethodSource("ru.otus.hw.repository.TestUtils#getDbAuthors")
    void authorById(Author expected) {
        final var actual = repo.findById(expected.getId());
        assertThat(actual).isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @DisplayName("загружает список всех авторов")
    @Test
    void authorsList() {
        final var actual = repo.findAll();
        final var expected = em.getEntityManager().createQuery("select a from Author a", Author.class).getResultList();

        assertThat(actual).containsExactlyElementsOf(expected);
        actual.forEach(System.out::println);
    }


}