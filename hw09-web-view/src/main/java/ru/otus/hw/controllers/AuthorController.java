package ru.otus.hw.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.hw.services.AuthorService;

@RequiredArgsConstructor
@Controller
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/books/authors")
    public String findAll(Model model) {
        model.addAttribute("authors", authorService.findAll());
        return "authors/all";
    }
}
