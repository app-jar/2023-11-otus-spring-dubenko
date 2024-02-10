package ru.otus.hw.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.domain.Author;
import ru.otus.hw.service.AuthorService;

@ShellCommandGroup("Author commands")
@ShellComponent
@RequiredArgsConstructor
public class AuthorShell {

	private final AuthorService authorService;

	@ShellMethod(value = "get all authors", key = {"author-all", "aa"})
	public String authorAll() {
		return String.join("\n", authorService.all().stream()
				.map(this::authorToString).toList());
	}

	@ShellMethod(value = "get author by id", key = {"author-get", "ag"})
	public String authorById(Long id) {
		return authorToString(authorService.get(id));
	}

	@ShellMethod(value = "create author with name", key = {"author-new", "an"})
	public String authorCreate(String name) {
		return authorToString(authorService.create(name));
	}

	@ShellMethod(value = "rename author with id", key = {"author-rename", "ar"})
	public String authorRename(Long id, String name) {
		return authorToString(authorService.rename(id, name));
	}

	@ShellMethod(value = "delete author with id", key = {"author-delete", "ad"})
	public void authorDelete(Long id) {
		authorService.delete(id);
	}

	private String authorToString(Author author) {
		return String.format("%5d, %s", author.id(), author.name());
	}
}
