package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.CategoryDao;
import ru.otus.hw.domain.Category;
import ru.otus.hw.domain.CategoryLink;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryDao categoryDao;

    @Override
    public List<Category> all() {
        return categoryDao.all();
    }

    @Override
    public Category get(Long id) {
        return categoryDao.byId(id);
    }

    @Override
    public Category create(String name) {
        return categoryDao.save(new Category(0L, name));
    }

    @Override
    public void delete(Long id) {
        categoryDao.deleteById(id);
    }

    @Override
    public List<Category> byBookId(Long bookId) {
        return categoryDao.byBookId(bookId);
    }

    @Override
    public Map<Long, List<Category>> bookIdCategoryMapping() {
        final var categoriesMapping = categoryDao.allEffective().stream()
                .collect(Collectors.toMap(Category::id, Function.identity()));

        return categoryDao.allLinks().stream()
                .collect(Collectors.groupingBy(
                        CategoryLink::bookId,
                        Collectors.mapping(
                                link -> categoriesMapping.get(link.categoryId()),
                                Collectors.toList())
                ));
    }

    @Override
    public void addCategoryToBook(Long bookId, List<Long> categoryIds) {
        categoryDao.insertLinks(categoryIds.stream()
                .map(categoryId -> new CategoryLink(bookId, categoryId)).toList());
    }

    @Override
    public void deleteCategoryForBook(Long bookId, Long categoryId) {
        categoryDao.deleteLink(new CategoryLink(bookId, categoryId));
    }
}
