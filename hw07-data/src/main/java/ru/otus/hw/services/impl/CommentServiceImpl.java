package ru.otus.hw.services.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.CommentRepository;
import ru.otus.hw.services.CommentService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repo;

    @Override
    @Transactional
    public Optional<CommentDto> findById(long id) {
        return repo.findById(id).map(CommentDto::new);
    }

    @Override
    @Transactional
    public List<CommentDto> findByBookId(long id) {
        return repo.findByBookId(id).stream().map(CommentDto::new).toList();
    }

    @Override
    @Transactional
    public CommentDto insert(Long bookId, String text) {
        return save(
                new Comment()
                        .setId(0)
                        .setBook(new Book().setId(bookId)),
                text
        );
    }

    @Override
    @Transactional
    public CommentDto update(long id, String text) {
        final var comment = repo.findById(id).orElseThrow(EntityNotFoundException::new);
        return save(comment, text);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        repo.findById(id)
                .ifPresent(repo::delete);
    }

    private CommentDto save(Comment comment, String text) {
        comment.setText(text);
        return new CommentDto(repo.save(comment));
    }
}
