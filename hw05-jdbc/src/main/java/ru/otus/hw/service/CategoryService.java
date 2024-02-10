package ru.otus.hw.service;

import ru.otus.hw.domain.Category;

import java.util.List;

public interface CategoryService {

    List<Category> all();

    Category get(Long id);

    Category create(String name);

    void delete(Long id);

    void addCategoryToBook(Long bookId, List<Long> categoryId);

    void deleteCategoryForBook(Long bookId, Long categoryId);
}
