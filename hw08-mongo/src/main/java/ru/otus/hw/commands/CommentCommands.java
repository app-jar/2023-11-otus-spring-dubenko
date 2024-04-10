package ru.otus.hw.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.converters.CommentConverter;
import ru.otus.hw.services.CommentService;

import java.util.stream.Collectors;

@SuppressWarnings({"SpellCheckingInspection", "unused"})
@RequiredArgsConstructor
@ShellComponent
public class CommentCommands {

    private final CommentService commentService;

    private final CommentConverter commentConverter;

    @ShellMethod(value = "Find all comments by book id", key = "cbbid")
    public String findByBookId(String id) {
        return commentService.findByBookId(id).stream()
                .map(commentConverter::commnetToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    @ShellMethod(value = "Find comment by id", key = "cbid")
    public String findById(String id) {
        return commentService.findById(id)
                .map(commentConverter::commnetToString)
                .orElse("Comment with id %d not found".formatted(id));
    }

    @ShellMethod(value = "Insert comment", key = "cins")
    public String insertComment(String bookId, String text) {
        var savedComment = commentService.insert(bookId, text);
        return commentConverter.commnetToString(savedComment);
    }

    @ShellMethod(value = "Update comment", key = "cupd")
    public String updateComment(String id, String text) {
        var savedComment = commentService.update(id, text);
        return commentConverter.commnetToString(savedComment);
    }

    @ShellMethod(value = "Delete comment by id", key = "cdel")
    public void deleteComment(String id) {
        commentService.deleteById(id);
    }

    @ShellMethod(value = "Delete all comments by book id", key = "dbbid")
    public void deleteByBookId(String id) {
        commentService.deleteByBookId(id);
    }
}
