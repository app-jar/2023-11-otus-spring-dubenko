package ru.otus.hw.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.hw.domain.Author;
import ru.otus.hw.domain.Book;
import ru.otus.hw.domain.Category;
import ru.otus.hw.service.BookService;

import java.util.List;

@ShellCommandGroup("Book commands")
@ShellComponent
@RequiredArgsConstructor
public class BookShell {

	private final BookService bookService;

	@ShellMethod(value = "get all books", key = {"book-all", "ba"})
	public String bookAll() {
		return String.join("\n", bookService.all().stream()
				.map(this::bookToString).toList());
	}

	@ShellMethod(value = "search books", key = {"book-search", "bs"})
	public String bookSearch(
			@ShellOption(value = {"n", "name"}, defaultValue = ShellOption.NULL) String name,
			@ShellOption(value = {"a", "author"}, defaultValue = ShellOption.NULL) String author
	) {
		return String.join("\n", bookService.search(name, author).stream()
				.map(this::bookToString).toList());
	}

	@ShellMethod(value = "get book by id", key = {"book-get", "bg"})
	public String bookById(Long id) {
		return bookToString(bookService.get(id));
	}

	@ShellMethod(value = "create book with name and author id, categories ids", key = {"book-new", "bn"})
	public String bookCreate(
			String name,
			Long authorId,
			@ShellOption(arity = 20) Long[] categoriesIds
	) {
		final List<Long> categoriesIdList = null == categoriesIds ? List.of() : List.of(categoriesIds);
		return bookToString(bookService.create(name, authorId, categoriesIdList));
	}

	@ShellMethod(value = "rename book with id", key = {"book-rename", "br"})
	public String bookRename(Long id, String name) {
		return bookToString(bookService.rename(id, name));
	}

	@ShellMethod(value = "change author of book with id", key = {"book-change-author", "bca"})
	public String bookChangeAuthor(Long id, Long authorId) {
		return bookToString(bookService.setAuthorId(id, authorId));
	}

	@ShellMethod(value = "delete book with id", key = {"book-delete", "bd"})
	public void bookDelete(Long id) {
		bookService.delete(id);
	}

	private String bookToString(Book book) {
		return bookInfoToString(book) + authorToString(book.author()) + categoriesToString(book.categories());
	}

	private String bookInfoToString(Book book) {
		return String.format("%5d, %s\n", book.id(), book.name());
	}

	private String authorToString(Author author) {
		return String.format("       %s (%d)\n", author.name(), author.id());
	}

	private String categoriesToString(List<Category> categories) {
		final var categoriesString = String.join(
				", ",
				categories.stream().map(this::categoryToString).toList()
		);
		return categories.isEmpty() ? "" : String.format("       %s\n", categoriesString);
	}

	private String categoryToString(Category category) {
		return String.format(AnsiOutput.toString(AnsiColor.BLUE, "#", AnsiColor.DEFAULT, "%s (%d)"),
				category.name(), category.id());
	}
}
