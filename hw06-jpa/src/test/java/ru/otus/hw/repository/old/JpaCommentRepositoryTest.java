package ru.otus.hw.repository.old;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.jpa.JpaCommentRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий комментариев ")
@DataJpaTest
@Import({JpaCommentRepository.class})
class JpaCommentRepositoryTest {

    @Autowired
    private JpaCommentRepository repo;

    @Autowired
    private TestEntityManager em;

    private List<Author> dbAuthors;

    private List<Genre> dbGenres;

    private List<Comment> dbComments;

    private List<Book> dbBooks;

    @BeforeEach
    void setUp() {
        dbAuthors = TestUtils.getDbAuthors();
        dbGenres = TestUtils.getDbGenres();
        dbComments = TestUtils.getDbComments();
        dbBooks = TestUtils.getDbBooks(dbAuthors, dbGenres);
    }

    @DisplayName("загружает комментарий по id")
    @ParameterizedTest
    @MethodSource("ru.otus.hw.repository.TestUtils#getDbComments")
    void searchById(Comment comment) {
        final var expectedComment = em.find(Comment.class, comment.getId());

        final var actualComment = repo.findById(comment.getId());

        assertThat(actualComment).isPresent()
                .get()
                .isEqualTo(expectedComment);
    }

    @DisplayName("загружет список всех комментариев по id книги")
    @Test
    void searchByBookId() {
        var actualComments = repo.findByBookId(3);
        var expectedComments = List.of(dbComments.get(2), dbComments.get(3));

        assertThat(actualComments)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("book")
                .containsExactlyElementsOf(expectedComments);
        actualComments.forEach(System.out::println);
    }

    @DisplayName("сохраняет новый комментарий")
    @Test
    void saveNewComment() {
        var expectedComment = new Comment(
                0,
                dbBooks.get(0),
                "Comment_10500"
        );

        var returnedComment = repo.save(expectedComment);
        assertThat(returnedComment).isNotNull()
                .matches(comment -> comment.getId() > 0)
                .usingRecursiveComparison().isEqualTo(expectedComment);

        assertThat(repo.findById(returnedComment.getId()))
                .isPresent()
                .get()
                .isEqualTo(returnedComment);
    }

    @DisplayName("сохраняет измененный комментарий")
    @Test
    void saveEditComment() {
        var expectedText = "Comment_10500";

        final var old = repo.findById(1L);

        assertThat(old)
                .isPresent()
                .get()
                .extracting(Comment::getText)
                .isNotEqualTo(expectedText);

        old.get().setText(expectedText);

        var returnedComment = repo.save(old.get());
        assertThat(returnedComment).isNotNull()
                .matches(comment -> comment.getId() > 0)
                .usingRecursiveComparison().isEqualTo(old.get());

        assertThat(repo.findById(returnedComment.getId()))
                .isPresent()
                .get()
                .extracting(Comment::getText)
                .isEqualTo(expectedText);
    }

    @DisplayName("удаляет комментарий по id ")
    @Test
    void deleteComment() {
        final var comment = em.find(Comment.class,1L);
        assertThat(comment).isNotNull();
        repo.deleteById(comment.getId());
        assertThat(em.find(Comment.class, comment.getId())).isNull();
    }

}