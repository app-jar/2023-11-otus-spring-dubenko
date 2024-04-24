package ru.otus.hw.controllers;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.exceptions.BadRequestException;
import ru.otus.hw.exceptions.NotFoundException;
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

    @GetMapping("api/v1/books/{id}")
    public BookDto getById(@PathVariable Long id) {
        return bookService.findById(id)
                .orElseThrow(() -> new NotFoundException("Book id = %d is not found".formatted(id)));
    }

    @DeleteMapping("api/v1/books/{id}")
    public void deleteById(@PathVariable Long id) {
        bookService.deleteById(id);
    }

    @PostMapping("api/v1/books/")
    public BookDto createBook(@RequestBody BookDto book) {
        try {
            return bookService.insert(book);
        } catch (EntityNotFoundException | IllegalArgumentException ex) {
            throw new BadRequestException(ex.getMessage());
        }
    }

    @PutMapping("api/v1/books/")
    public BookDto updateBook(@RequestBody BookDto book) {
        try {
            return bookService.update(book);
        } catch (EntityNotFoundException | IllegalArgumentException ex) {
            throw new BadRequestException(ex.getMessage());
        }
    }

}
