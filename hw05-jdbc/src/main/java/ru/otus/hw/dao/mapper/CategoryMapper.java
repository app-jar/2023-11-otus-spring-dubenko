package ru.otus.hw.dao.mapper;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.otus.hw.domain.Category;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CategoryMapper implements RowMapper<Category> {
    @Override
    public Category mapRow(ResultSet rs, int index) throws SQLException, DataAccessException {
        return new Category()
                .id(rs.getLong("id"))
                .name(rs.getString("name"));
    }
}
