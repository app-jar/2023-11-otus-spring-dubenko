package ru.otus.hw.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.hw.dao.mapper.CategoryMapper;
import ru.otus.hw.dao.mapper.LinkMapper;
import ru.otus.hw.domain.Category;
import ru.otus.hw.domain.CategoryLink;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class JDBCCategoryRepository implements CategoryDao {

    private final NamedParameterJdbcOperations jdbc;

    private final CategoryMapper categoryMapper;

    private final LinkMapper linkMapper;

    @Override
    public List<Category> all() {
        return jdbc.query(
                "select id, name from category",
                Map.of(),
                categoryMapper
        );
    }

    @Override
    public List<Category> allEffective() {
        return jdbc.query("""
                select id, name from category
                where exists (select category_id from Book_Category where category_id = id)
                """,
                Map.of(),
                categoryMapper
        );
    }

    @Override
    public Category byId(Long id) {
        return jdbc.queryForObject(
                "select id, name from category where id = :id",
                Map.of("id", id),
                categoryMapper
        );
    }

    @Override
    public void deleteById(Long id) {
        jdbc.update(
                "delete from category where id = :id",
                Map.of("id", id)
        );
    }

    @Override
    public Category save(Category category) {
        if (null == category.id() || 0 == category.id()) {
            return insert(category);
        }

        return update(category);
    }

    @Override
    public List<Category> byBookId(Long id) {
        return jdbc.query("""
                select c.id id, c.name name from category c join Book_Category link
                on link.category_id = c.id
                where link.book_id = :book_id
                """,
                Map.of("book_id", id),
                categoryMapper
        );
    }

    @Override
    public List<CategoryLink> allLinks() {
        return jdbc.query("select category_id id, book_id from Book_Category",
                Map.of(),
                linkMapper
        );
    }

    @Override
    public void deleteLink(CategoryLink link) {
        jdbc.update(
                "delete from Book_Category where category_id = :category_id and book_id = :book_id",
                Map.of("category_id", link.categoryId(), "book_id", link.bookId())
        );
    }

    @Override
    public void insertLinks(List<CategoryLink> links) {
        jdbc.batchUpdate(
                "insert into Book_Category (category_id, book_id) values (:category_id, :book_id)",
                links.stream().map(link -> Map.of("category_id", link.categoryId(), "book_id", link.bookId()))
                        .map(MapSqlParameterSource::new)
                        .toList().toArray(MapSqlParameterSource[]::new)
        );
    }

    private Category update(Category category) {
        jdbc.update(
                "update category set name = :name where id = :id",
                Map.of("id", category.id(), "name", category.name())
        );

        return byId(category.id());
    }

    private Category insert(Category category) {
        final var keyHolder = new GeneratedKeyHolder();
        jdbc.update(
                "insert into category (name) values (:name)",
                new MapSqlParameterSource(Map.of("name", category.name())),
                keyHolder,
                new String[] {"id"}
        );

        final var id = Objects.requireNonNull(keyHolder.getKey()).longValue();
        return byId(id);
    }
}
