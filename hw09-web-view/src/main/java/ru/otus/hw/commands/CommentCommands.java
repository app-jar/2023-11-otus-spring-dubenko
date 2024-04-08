package ru.otus.hw.commands;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.converters.CommentConverter;
import ru.otus.hw.services.CommentService;

import java.util.stream.Collectors;

@RequiredArgsConstructor

public class CommentCommands {

    private final CommentService commentService;

    private final CommentConverter commentConverter;


    public String findAllCommentsWithPaging(
            int page,
            int limit
    ) {
        return commentService.page(page, limit).stream()
                .map(commentConverter::commnetToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }


    public String findByBookId(Long id) {
        return commentService.findByBookId(id).stream()
                .map(commentConverter::commnetToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }


    public String findById(long id) {
        return commentService.findById(id)
                .map(commentConverter::commnetToString)
                .orElse("Comment with id %d not found".formatted(id));
    }


    public String insertComment(Long bookId, String text) {
        var savedComment = commentService.insert(bookId, text);
        return commentConverter.commnetToString(savedComment);
    }


    public String updateComment(long id, String text) {
        var savedComment = commentService.update(id, text);
        return commentConverter.commnetToString(savedComment);
    }


    public void deleteComment(long id) {
        commentService.deleteById(id);
    }
}
