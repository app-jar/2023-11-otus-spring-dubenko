package ru.otus.hw;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.hw.dao.BookDao;
import ru.otus.hw.domain.Book;
import ru.otus.hw.domain.BookSearch;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.otus.hw.BookDaoConstants.*;

@JdbcTest
@Import(BookDaoTestConfig.class)
@DisplayName("Класс BookDao")
class BookDaoTest {

	@Autowired
	BookDao bookDao;

	@Test
	@DisplayName("возвращает все книги в правильном количестве")
	void allSize() {
		assertThat(bookDao.all()).hasSize(BOOKS.size());
	}

	@Test
	@DisplayName("возвращает все книги с правильными жанрами")
	void allCategories() {
		final var books = bookDao.all();
		assertThat(books.get(0).categories()).containsOnly(CATEGORY_1, CATEGORY_2);
	}

	@Test
	@DisplayName("возвращает првильную книгу по id")
	void getById() {
		assertThat(bookDao.byId(1L)).isEqualTo(BOOK_1);
	}

	@Test
	@DisplayName("возвращает првильное количество книг при поиске по автору")
	void searchByAuthorSize() {
		assertThat(bookDao.search(new BookSearch("", "a1"))).hasSize(2);
	}

	@Test
	@DisplayName("возвращает првильное количество книг при поиске по названию")
	void searchByBookSize() {
		assertThat(bookDao.search(new BookSearch( "a1", ""))).hasSize(2);
	}

	@Test
	@DisplayName("возвращает првильную книгу при поиске по автору")
	void searchByAuthorCorrect() {
		assertThat(bookDao.search(new BookSearch("", "a2")).get(0))
				.isEqualTo(BOOK_3);
	}

	@Test
	@DisplayName("возвращает првильную книгу при поиске по названию")
	void searchByBookCorrect() {
		assertThat(bookDao.search(new BookSearch( "a2", "")).get(0))
				.isEqualTo(BOOK_3);
	}

	@Test
	@DisplayName("удаялет книгу корректно")
	void delete() {
		bookDao.deleteById(1L);

		assertThat(bookDao.all()).containsOnly(BOOK_2, BOOK_3);
	}

	@Test
	@DisplayName("добавляет книгу корректно")
	void add() {
		bookDao.save(new Book()
				.name(BOOK_ADD.name())
				.author(BOOK_ADD.author())
				.categories(BOOK_ADD.categories())
		);

		assertThat(bookDao.byId(BOOK_ADD.id())).isEqualTo(BOOK_ADD);
	}

	@Test
	@DisplayName("изменяет книгу корректно")
	void edit() {
		final var toEdit = new Book()
				.id(BOOK_2.id())
				.name(BOOK_2.name())
				.author(AUTHOR_2);

		bookDao.save(toEdit);

		assertThat(bookDao.byId(BOOK_2.id())).isEqualTo(toEdit);
	}
}
