package ru.otus.hw.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import ru.otus.hw.models.Comment;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {

    List<Comment> findByBookId(long id);

    @EntityGraph("entity-graph-comment-book")
    Slice<Comment> findAll(Pageable pageable);

}
