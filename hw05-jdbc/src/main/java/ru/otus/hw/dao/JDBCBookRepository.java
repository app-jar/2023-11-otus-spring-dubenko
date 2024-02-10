package ru.otus.hw.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import ru.otus.hw.dao.mapper.BookMapper;
import ru.otus.hw.domain.Book;
import ru.otus.hw.domain.BookSearch;
import ru.otus.hw.domain.Category;
import ru.otus.hw.domain.CategoryLink;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class JDBCBookRepository implements BookDao {

    private final NamedParameterJdbcOperations jdbc;

    private final CategoryDao categoryDao;

    private final BookMapper mapper;

    @Override
    public List<Book> all() {
        final var books =  jdbc.query(
                """
                select a.id author_id, a.name author_name, b.id book_id, b.name book_name
                from book b left join author a on b.author_id = a.id
                """,
                Map.of(),
                mapper
        );
        if (!books.isEmpty()) {
            enrichWithCategories(books);
        }
        return books;
    }

    @Override
    public Book byId(Long id) {
        final var book = jdbc.queryForObject(
                """
                select a.id author_id, a.name author_name, b.id book_id, b.name book_name
                from book b left join author a on b.author_id = a.id
                where b.id = :id
                """,
                Map.of("id", id),
                mapper
        );
        if (null != book) {
            enrichWithCategories(book);
        }
        return book;
    }

    @Override
    public List<Book> search(BookSearch searchDto) {
        final var books =  jdbc.query(
                """
                select a.id author_id, a.name author_name, b.id book_id, b.name book_name
                from book b left join author a on b.author_id = a.id
                where 1 = 1
                """ + searchWhereClause(searchDto),
                searchParams(searchDto),
                mapper
        );
        if (!books.isEmpty()) {
            enrichWithCategories(books);
        }
        return books;
    }

    @Override
    public void deleteById(Long id) {
        jdbc.update("delete from book where id = :id", Map.of("id", id));
    }

    @Override
    public Book save(Book book) {
        if (null == book.id() || 0 == book.id()) {
            return insert(book);
        }

        return update(book);
    }

    private Book update(Book book) {
        jdbc.update(
                "update book set name = :name, author_id = :author_id where id = :id",
                Map.of("id", book.id(), "name", book.name(),
                        "author_id", book.author().id())
        );

        return byId(book.id());
    }

    private Book insert(Book book) {
        final var keyHolder = new GeneratedKeyHolder();
        jdbc.update(
                "insert into book (name, author_id) values (:name, :author_id)",
                new MapSqlParameterSource(Map.of("name", book.name(),
                        "author_id", book.author().id())),
                keyHolder,
                new String[] {"id"}
        );

        final var id = Objects.requireNonNull(keyHolder.getKey()).longValue();

        insertLinks(id, book.categories());

        return byId(id);
    }

    private Map<String, ?> searchParams(BookSearch search) {
        final var params = new HashMap<String, Object>();

        if (StringUtils.hasLength(search.authorNameQuery())) {
           params.put("author", search.authorNameQuery());
        }

        if (StringUtils.hasLength(search.nameQuery())) {
            params.put("name", search.nameQuery());
        }

        return params;
    }

    private String searchWhereClause(BookSearch search) {
        final var where = new StringBuilder();

        if (StringUtils.hasLength(search.authorNameQuery())) {
            where.append(" and a.name ilike '%' || :author || '%'");
        }

        if (StringUtils.hasLength(search.nameQuery())) {
            where.append(" and b.name ilike '%' || :name || '%'");
        }

        return where.toString();
    }

    private void enrichWithCategories(Book book) {
        book.categories(categoryDao.byBookId(book.id()));
    }

    private void enrichWithCategories(List<Book> books) {
        final var categoriesMapping = categoriesMapping();
        final var linkMapping = linkMapping();

        for (var book : books) {
            enrichWithCategories(book, linkMapping, categoriesMapping);
        }
    }

    private void enrichWithCategories(
            Book book,
            Map<Long, List<CategoryLink>> linksMapping,
            Map<Long, Category> categoriesMapping
    ) {
        final var categoryLinks = linksMapping.get(book.id());
        if (null == categoryLinks) {
            return;
        }
        final var categories = categoryLinks.stream()
                .map(CategoryLink::categoryId)
                .map(categoriesMapping::get).toList();

        book.categories(categories);
    }

    private Map<Long, Category> categoriesMapping() {
        return categoryDao.allEffective().stream()
                .collect(Collectors.toMap(Category::id, Function.identity()));
    }

    private Map<Long, List<CategoryLink>> linkMapping() {
        return categoryDao.allLinks().stream()
                .collect(Collectors.groupingBy(CategoryLink::bookId));
    }

    private void insertLinks(long id, List<Category> categories) {
        final var links = categories.stream().map(category -> new CategoryLink(id, category.id())).toList();
        categoryDao.insertLinks(links);
    }
}
