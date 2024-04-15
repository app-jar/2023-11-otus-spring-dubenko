package ru.otus.hw.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BookController {

    private final BookService bookService;

    @GetMapping("api/v1/books/")
    public List<BookDto> searchByTitle(@RequestParam String title) {
        if (StringUtils.hasLength(title)) {
            final var books = bookService.findAllByTitleQuery(title);
            return books;
        }

        return bookService.findAll();
    }

    @DeleteMapping("api/v1/books/{id}")
    public void deleteById(@PathVariable Long id) {
        bookService.deleteById(id);
    }
}
