package ru.otus.hw.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.services.CommentService;

@RequiredArgsConstructor
@Controller
public class CommentController {

    private final CommentService commentService;

    @PostMapping("api/comments/")
    public CommentDto createComment(@RequestBody CommentDto comment) {
        return commentService.insert(comment);
    }

    @DeleteMapping("api/comments/{commentId}")
    public void removeComment(@PathVariable Long commentId) {
        commentService.deleteById(commentId);
    }
}
