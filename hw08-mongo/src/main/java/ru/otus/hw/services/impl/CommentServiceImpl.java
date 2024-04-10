package ru.otus.hw.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.dto.mapper.CommentMapper;
import ru.otus.hw.exceptions.NotFoundException;
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
    public Optional<CommentDto> findById(String id) {
        return repo.findById(id)
                .map(CommentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> findByBookId(String id) {
        return repo.findAllByBookId(id).stream()
                .map(CommentMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public CommentDto insert(String bookId, String text) {
        final var book = bookRepo.findById(bookId)
                .orElseThrow(() -> new NotFoundException("Book does not exists, id = " + bookId));

        final var comment = new Comment()
                .setId(null)
                .setText(text);

        return CommentMapper.toDto(repo.save(comment));
    }

    @Override
    @Transactional
    public CommentDto update(String id, String text) {
        final var comment = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Comment does not exists, id = " + id));

        comment.setText(text);
        return CommentMapper.toDto(repo.save(comment));
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        repo.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteByBookId(String id) {
        repo.deleteAllByBookId(id);
    }

}
