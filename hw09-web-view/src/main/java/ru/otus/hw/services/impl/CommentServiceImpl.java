package ru.otus.hw.services.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.dto.mapper.CommentMapper;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;
import ru.otus.hw.services.CommentService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repo;

    private final BookRepository bookRepo;

    @Override
    @Transactional(readOnly = true)
    public Optional<CommentDto> findById(long id) {
        return repo.findById(id)
                .map(CommentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> findByBookId(long id, int page, int limit) {
        return repo.findAll(id, PageRequest.of(page, limit)).stream()
                .toList();
    }

    @Override
    @Transactional
    public CommentDto insert(Long bookId, String text) {
        final var book = bookRepo.findById(bookId)
                .orElseThrow(EntityNotFoundException::new);

        final var comment = new Comment()
                .setId(0)
                .setBook(book)
                .setText(text);

        return CommentMapper.toDto(repo.save(comment));
    }

    @Override
    @Transactional
    public CommentDto update(long id, String text) {
        final var comment = repo.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        comment.setText(text);
        return CommentMapper.toDto(repo.save(comment));
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        repo.deleteById(id);
    }
}
