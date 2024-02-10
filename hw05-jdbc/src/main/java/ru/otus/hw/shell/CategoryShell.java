package ru.otus.hw.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.domain.Category;
import ru.otus.hw.service.CategoryService;

import java.util.List;

@ShellCommandGroup("Category commands")
@ShellComponent
@RequiredArgsConstructor
public class CategoryShell {

	private final CategoryService categoryService;

	@ShellMethod(value = "get all categories", key = {"category-all", "ca"})
	public String categoryAll() {
		return String.join("\n", categoryService.all().stream()
				.map(this::categoryToString).toList());
	}

	@ShellMethod(value = "get category by id", key = {"category-get", "cg"})
	public String categoryById(Long id) {
		return categoryToString(categoryService.get(id));
	}

	@ShellMethod(value = "create category with name", key = {"category-new", "cn"})
	public String categoryCreate(String name) {
		return categoryToString(categoryService.create(name));
	}

	@ShellMethod(value = "delete category with id", key = {"category-delete", "cd"})
	public void categoryDelete(Long id) {
		categoryService.delete(id);
	}

	@ShellMethod(value = "add category to book with id", key = {"category-add-book", "cab"})
	public void bookAddCategory(Long bookId, Long categoryId) {
		categoryService.addCategoryToBook(bookId, List.of(categoryId));
	}

	@ShellMethod(value = "delete category for book with id", key = {"category-delete-book", "cdb"})
	public void bookDeleteCategory(Long bookId, Long categoryId) {
		categoryService.deleteCategoryForBook(bookId, categoryId);
	}

	private String categoryToString(Category category) {
		return String.format("%5d, %s", category.id(), category.name());
	}
}
