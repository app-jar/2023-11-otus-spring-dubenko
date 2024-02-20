package ru.otus.hw.repositories;

import ru.otus.hw.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    List<Comment> findByBookId(long id);

    Optional<Comment> findById(long id);

    Comment save(Comment comment);

    void delete(Comment comment);
}
