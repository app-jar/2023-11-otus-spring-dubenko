package ru.otus.hw.dto.mapper;

import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.models.Comment;

public class CommentMapper {

    public static CommentDto toDto(Comment comment) {
        return new CommentDto(comment.getId(), comment.getBook().getTitle(), comment.getText());
    }

    public static Comment toModel(CommentDto comment) {
        return new Comment().setId(comment.getId()).setText(comment.getText());
    }
}
