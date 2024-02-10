package ru.otus.hw.dao;

import ru.otus.hw.domain.Category;
import ru.otus.hw.domain.CategoryLink;

import java.util.List;

public interface CategoryDao {

    List<Category> all();

    List<Category> allEffective();

    Category byId(Long id);

    void deleteById(Long id);

    Category save(Category book);

    List<Category> byBookId(Long id);

    List<CategoryLink> allLinks();

    void deleteLink(CategoryLink link);

    void insertLinks(List<CategoryLink> link);
}
