package ru.otus.hw.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.exceptions.NotFoundException;
import ru.otus.hw.services.BookService;

import java.util.List;

@RequiredArgsConstructor
@RestController()
public class BookController {

    private final BookService bookService;

    @GetMapping("api/books")
    public List<BookDto> allBooks() {
        return bookService.findAll();
    }

    @GetMapping(value = "api/books", params = "title")
    public List<BookDto> searchByTitle(@RequestParam(required = false, defaultValue = "") String title) {
        return bookService.findAllByTitleQuery(title);
    }

    @GetMapping("api/books/{id}")
    public BookDto getById(@PathVariable Long id) {
        return bookService.findById(id)
                .orElseThrow(() -> new NotFoundException("Book id = %d is not found".formatted(id)));
    }

    @PostMapping("api/books")
    public BookDto createBook(@RequestBody BookCreateDto book) {
        return bookService.insert(book);
    }

    @PutMapping("api/books/{id}")
    public BookDto updateBook(@Valid @ModelAttribute("book") BookUpdateDto book, @PathVariable String bookId) {
        return bookService.update(book);
    }

    @DeleteMapping("api/books/{id}")
    public void deleteById(@PathVariable Long id) {
        bookService.deleteById(id);
    }
}
