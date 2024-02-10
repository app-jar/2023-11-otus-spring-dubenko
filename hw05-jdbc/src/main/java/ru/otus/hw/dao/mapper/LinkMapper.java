package ru.otus.hw.dao.mapper;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.otus.hw.domain.CategoryLink;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class LinkMapper implements RowMapper<CategoryLink> {
    @Override
    public CategoryLink mapRow(ResultSet rs, int index) throws SQLException, DataAccessException {
        return new CategoryLink()
                .categoryId(rs.getLong("category_id"))
                .bookId(rs.getLong("book_id"));
    }
}
