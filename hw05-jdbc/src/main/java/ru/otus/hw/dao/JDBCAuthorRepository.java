package ru.otus.hw.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.hw.dao.mapper.AuthorMapper;
import ru.otus.hw.domain.Author;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class JDBCAuthorRepository implements AuthorDao {

    private final NamedParameterJdbcOperations jdbc;

    private final AuthorMapper mapper;

    @Override
    public List<Author> all() {
        return jdbc.query(
                "select id, name from author",
                Map.of(),
                mapper
        );
    }

    @Override
    public Author byId(Long id) {
        return jdbc.queryForObject(
                "select id, name from author where id = :id",
                Map.of("id", id),
                mapper
        );
    }

    @Override
    public void deleteById(Long id) {
        jdbc.update(
                "delete from author where id = :id",
                Map.of("id", id)
        );
    }

    @Override
    public Author save(Author author) {
        if (null == author.id() || 0 == author.id()) {
            return insert(author);
        }

        return update(author);
    }

    private Author update(Author author) {
        jdbc.update(
                "update author set name = :name where id = :id",
                Map.of("id", author.id(), "name", author.name())
        );

        return byId(author.id());
    }

    private Author insert(Author author) {
        final var keyHolder = new GeneratedKeyHolder();
        jdbc.update(
                "insert into author (name) values (:name)",
                new MapSqlParameterSource(Map.of("name", author.name())),
                keyHolder,
                new String[] {"id"}
        );

        final var id = Objects.requireNonNull(keyHolder.getKey()).longValue();
        return byId(id);
    }
}
