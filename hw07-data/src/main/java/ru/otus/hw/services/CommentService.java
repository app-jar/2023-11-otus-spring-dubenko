package ru.otus.hw.services;

import ru.otus.hw.dto.CommentDto;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    Optional<CommentDto> findById(long id);

    List<CommentDto> findByBookId(long id);

    List<CommentDto> page(int page, int limit);

    CommentDto insert(Long bookId, String text);

    CommentDto update(long id, String text);

    void deleteById(long id);
}
