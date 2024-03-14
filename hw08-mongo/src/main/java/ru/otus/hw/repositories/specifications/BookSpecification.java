package ru.otus.hw.repositories.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.otus.hw.models.Book;

public class BookSpecification {

    public static Specification<Book> titleQuery(String titleQuery) {
        return null == titleQuery ? null :
                (rs, query, cb) -> cb.like(rs.get("title"), "%" + titleQuery + "%");
    }
}
