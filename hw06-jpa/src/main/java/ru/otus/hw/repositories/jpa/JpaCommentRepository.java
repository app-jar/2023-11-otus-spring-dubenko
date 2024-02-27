package ru.otus.hw.repositories.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.CommentRepository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaCommentRepository implements CommentRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<Comment> findByBookId(long id) {
         final var query = em.createQuery("select c from Comment c where c.book.id = :book_id", Comment.class);
         query.setParameter("book_id", id);
         return query.getResultList();
    }

    @Override
    public Optional<Comment> findById(long id) {
        return Optional.ofNullable(em.find(Comment.class, id));
    }

    @Override
    public Comment save(Comment comment) {
        if (0 != comment.getId()) {
            return em.merge(comment);
        }
        em.persist(comment);
        return comment;
    }

    @Override
    public void deleteById(Long id) {
        final var toDelete = em.find(Comment.class, id);
        if (null != toDelete) {
            em.remove(toDelete);
        }
    }
}
