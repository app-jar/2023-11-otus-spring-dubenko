package ru.otus.hw.dao.mapper;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.otus.hw.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AuthorMapper implements RowMapper<Author> {
    @Override
    public Author mapRow(ResultSet rs, int index) throws SQLException, DataAccessException {
        return new Author()
                .id(rs.getLong("id"))
                .name(rs.getString("name"));
    }
}
