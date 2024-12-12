package ru.otus.hw.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.services.GenreService;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class GenreController {

    private final GenreService genreService;

    @GetMapping("/books/genres")
    public String page(Model model) {
        model.addAttribute("genres", genreService.findAll());
        return "genres/all";
    }

    @GetMapping("api/v1/genres")
    public ResponseEntity<List<GenreDto>> findAll() {
        return ResponseEntity.ok(genreService.findAll());
    }
}
