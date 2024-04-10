package ru.otus.hw.repositories;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.otus.hw.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    @EntityGraph("entity-graph-book-author-genres")
    List<Book> findAll();

    @EntityGraph("entity-graph-book-author-genres")
    Optional<Book> findById(Long id);

    @EntityGraph("entity-graph-book-author-genres")
    List<Book> findAll(Specification<Book> spec);
}
