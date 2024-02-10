package ru.otus.hw;

import ru.otus.hw.domain.Author;
import ru.otus.hw.domain.Book;
import ru.otus.hw.domain.Category;

import java.util.List;

public class BookDaoConstants {

    public static final Author AUTHOR_1 = new Author(1L, "a1");
    public static final Author AUTHOR_2 = new Author(2L, "a2");

    public static final Category CATEGORY_1 = new Category(1L, "c1");
    public static final Category CATEGORY_2 = new Category(2L, "c2");
    public static final Category CATEGORY_3 = new Category(3L, "c3");

    public static final Book BOOK_1 = new Book(1L, "a1b1", AUTHOR_1, List.of(CATEGORY_1, CATEGORY_2));
    public static final Book BOOK_2 = new Book(2L, "a1b2", AUTHOR_1, List.of());
    public static final Book BOOK_3 = new Book(3L, "a2b1", AUTHOR_2, List.of(CATEGORY_3));
    public static final Book BOOK_ADD = new Book(4L, "a2b2", AUTHOR_2, List.of(CATEGORY_2, CATEGORY_3));

    public static final List<Author> AUTHORS = List.of(
            AUTHOR_1,
            AUTHOR_2
    );

    public static final List<Category> CATEGORIES = List.of(
            CATEGORY_1,
            CATEGORY_2,
            CATEGORY_3
    );

    public static final List<Book> BOOKS = List.of(
            BOOK_1,
            BOOK_2,
            BOOK_3
    );
}
