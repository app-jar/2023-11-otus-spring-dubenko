package ru.otus.hw.dto.mapper;

import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;

public class CommentMapper {

    public static CommentDto toDto(Comment comment) {
        return new CommentDto(comment.getId(), comment.getText());
    }

    public static Comment toModel(CommentDto comment) {
        return new Comment().setId(comment.id()).setText(comment.text());
    }
}
