package ru.otus.hw;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;
import ru.otus.hw.repositories.handlers.BookMongoEventListener;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@Import({BookMongoEventListener.class})
public class BookMongoEventListenerTest {
    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    BookMongoEventListener listener;

    @Test
    public void deleteCommentTest() {
        final var comments = mongoTemplate.findAll(Comment.class);

        mongoTemplate.remove(comments.get(0).getBook());

        final var actual = mongoTemplate.findAll(Comment.class);

        assertThat(actual).doesNotContain(comments.get(0));
    }
}
