package ru.otus.hw.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.models.Comment;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {

    @Query("""
        select new ru.otus.hw.dto.CommentDto(c.id, b.title, c.text)
        from Comment c join Book b on c.book.id = b.id
        where :id = 0 or b.id = :id
        """)
    List<CommentDto> findAll(long id, Pageable pageable);
}
