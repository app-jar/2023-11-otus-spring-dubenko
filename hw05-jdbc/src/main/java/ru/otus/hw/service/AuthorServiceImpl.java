package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.AuthorDao;
import ru.otus.hw.domain.Author;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;

    @Override
    public List<Author> all() {
        return authorDao.all();
    }

    @Override
    public Author get(Long id) {
        return authorDao.byId(id);
    }

    @Override
    public Author rename(Long id, String newName) {
        return authorDao.save(new Author(id, newName));
    }

    @Override
    public Author create(String name) {
        return authorDao.save(new Author(0L, name));
    }

    @Override
    public void delete(Long id) {
        authorDao.deleteById(id);
    }
}
