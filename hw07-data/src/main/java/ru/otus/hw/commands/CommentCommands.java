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
    public String findByBookId(Long id) {
        return commentService.findByBookId(id).stream()
                .map(commentConverter::commnetToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    @ShellMethod(value = "Find comment by id", key = "cbid")
    public String findById(long id) {
        return commentService.findById(id)
                .map(commentConverter::commnetToString)
                .orElse("Comment with id %d not found".formatted(id));
    }

    // bins newBook 1 1,6
    @ShellMethod(value = "Insert comment", key = "cins")
    public String insertComment(Long bookId, String text) {
        var savedComment = commentService.insert(bookId, text);
        return commentConverter.commnetToString(savedComment);
    }

    // bupd 4 editedBook 3 2,5
    @ShellMethod(value = "Update comment", key = "cupd")
    public String updateComment(long id, String text) {
        var savedComment = commentService.update(id, text);
        return commentConverter.commnetToString(savedComment);
    }

    // bdel 4
    @ShellMethod(value = "Delete comment by id", key = "cdel")
    public void deleteComment(long id) {
        commentService.deleteById(id);
    }
}
