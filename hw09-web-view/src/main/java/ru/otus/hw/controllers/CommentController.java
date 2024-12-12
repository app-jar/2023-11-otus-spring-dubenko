package ru.otus.hw.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.hw.services.CommentService;

@RequiredArgsConstructor
@Controller
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comments/add")
    public String comments(@RequestParam long bookId, @RequestParam String text) {
        commentService.insert(bookId, text);
        return "redirect:/books/" + bookId;
    }

    @DeleteMapping("/comments/{commentId}")
    public String createComment(@RequestParam long bookId, @PathVariable Long commentId) {
        commentService.deleteById(commentId);
        return "redirect:/books/" + bookId;
    }
}
