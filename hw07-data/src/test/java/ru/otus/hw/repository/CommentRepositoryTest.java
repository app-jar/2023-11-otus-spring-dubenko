package ru.otus.hw.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.CommentRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий комментариев ")
@DataJpaTest
class CommentRepositoryTest {

    @Autowired
    private CommentRepository repo;

    @Test
    @DisplayName("работает с пагинацией")
    void paging() {
        final var actual = repo.findAll(PageRequest.of(1, 2))
                .stream().map(CommentDto::id).toList();

        assertThat(actual).containsExactlyElementsOf(List.of(3L, 4L));
    }

}