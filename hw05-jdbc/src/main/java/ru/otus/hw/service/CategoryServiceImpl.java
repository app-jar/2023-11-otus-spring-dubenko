package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.CategoryDao;
import ru.otus.hw.domain.Category;
import ru.otus.hw.domain.CategoryLink;

import java.util.List;

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
    public void addCategoryToBook(Long bookId, List<Long> categoryIds) {
        categoryDao.insertLinks(categoryIds.stream()
                .map(categoryId -> new CategoryLink(bookId, categoryId)).toList());
    }

    @Override
    public void deleteCategoryForBook(Long bookId, Long categoryId) {
        categoryDao.deleteLink(new CategoryLink(bookId, categoryId));
    }
}
