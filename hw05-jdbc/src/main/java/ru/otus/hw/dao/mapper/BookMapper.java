package ru.otus.hw.dao.mapper;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.otus.hw.domain.Author;
import ru.otus.hw.domain.Book;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class BookMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet rs, int index) throws SQLException, DataAccessException {
        return new Book()
                .id(rs.getLong("book_id"))
                .name(rs.getString("book_name"))
                .author(new Author()
                        .id(rs.getLong("author_id"))
                        .name(rs.getString("author_name"))
                );
    }
}
