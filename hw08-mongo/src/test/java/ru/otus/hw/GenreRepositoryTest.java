package ru.otus.hw;

import de.flapdoodle.embed.mongo.transitions.MongodStarter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.hw.models.Book;
import ru.otus.hw.repositories.GenreRepository;
import ru.otus.hw.repositories.impl.GenreRepositoryImpl;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@Import(GenreRepositoryImpl.class)
public class GenreRepositoryTest {
    @Autowired
    MongoTemplate mongoTemplate;

    @BeforeEach
    public void before() {
        mongoTemplate.getDb().drop();
    }
    @Autowired
    GenreRepository repository;

    @Test
    public void allGenresTest() {
        mongoTemplate.insert(List.of(
                new Book()
                        .setId(100L)
                        .setGenres(List.of("g1", "g2", "g3")),

                new Book()
                        .setId(200L)
                        .setGenres(List.of("g1", "g4")),

                new Book()
                        .setId(300L)
                        .setGenres(List.of())
        ), Book.class);

        assertThat(repository.findAll())
                .containsExactlyInAnyOrderElementsOf(List.of("g1", "g2", "g3", "g4"));
    }
}
