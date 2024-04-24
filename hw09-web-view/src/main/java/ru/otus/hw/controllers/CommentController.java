package ru.otus.hw.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.hw.services.CommentService;

@RequiredArgsConstructor
@Controller
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/books/comments")
    public String comments(
            Model model,
            @RequestParam(required = false, defaultValue = "0") Long bookId,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer limit
    ) {
        model.addAttribute("comments",
                commentService.findByBookId(bookId, page, limit));

        model.addAttribute("bookId", bookId);
        model.addAttribute("page", page);
        model.addAttribute("limit", limit);

        return "comments/all";
    }
}
