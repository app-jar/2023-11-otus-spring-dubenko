package ru.otus.hw.dao;

import ru.otus.hw.domain.Book;
import ru.otus.hw.domain.BookSearch;

import java.util.List;

public interface BookDao {

    List<Book> all();

    Book byId(Long id);

    List<Book> search(BookSearch searchDto);

    void deleteById(Long id);

    Book save(Book book);

}
