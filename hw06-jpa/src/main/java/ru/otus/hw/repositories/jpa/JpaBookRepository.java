package ru.otus.hw.repositories.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Book;
import ru.otus.hw.repositories.BookRepository;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.FETCH;

@Repository
@RequiredArgsConstructor
public class JpaBookRepository implements BookRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Optional<Book> findById(long id) {
        final var eg = em.getEntityGraph("entity-graph-book-author-genres");
        final var query = em.createQuery("select distinct b from Book b where id = :id", Book.class);
        query.setParameter("id", id);
        query.setHint(FETCH.getKey(), eg);
        return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    public List<Book> findAll() {
        final var eg = em.getEntityGraph("entity-graph-book-author-genres");

        final var query = em.createQuery("select distinct b from Book b", Book.class);
        query.setHint(FETCH.getKey(), eg);
        return query.getResultList();
    }

    @Override
    public Book save(Book book) {
        if (0 == book.getId()) {
            em.persist(book);
            return book;
        }
        return em.merge(book);
    }

    @Override
    public void deleteById(long id) {
        final var toRemove = em.find(Book.class, id);
        if (null != toRemove) {
            em.remove(toRemove);
        }
    }
}
