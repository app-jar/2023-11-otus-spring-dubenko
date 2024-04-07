package ru.otus.hw;

import com.mongodb.client.MongoDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.CommentRepository;
import ru.otus.hw.repositories.handlers.BookMongoEventListener;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@Import({BookMongoEventListener.class})
public class BookMongoEventListenerTest {
    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    BookMongoEventListener listener;

    @BeforeEach
    public void before() {
        mongoTemplate.getDb().drop();
    }

    @Test
    public void deleteCommentTest() {
        final var book1 = new Book().setTitle("B1").setId(1L);
        final var book2 = new Book().setTitle("B2").setId(2L);
        mongoTemplate.insert(List.of(
                book1,
                book2
        ), Book.class);

        mongoTemplate.insert(List.of(
                new Comment().setId(1L).setText("text").setBook(book1),
                new Comment().setId(2L).setText("text").setBook(book1),
                new Comment().setId(3L).setText("text").setBook(book2),
                new Comment().setId(4L).setText("text").setBook(book2)
        ), Comment.class);

        mongoTemplate.remove(book1);

        final var commentIds = mongoTemplate.findAll(Comment.class).stream()
                .map(Comment::getId)
                .toList();

        assertThat(commentIds).containsExactlyInAnyOrderElementsOf(List.of(3L, 4L));
    }
}
