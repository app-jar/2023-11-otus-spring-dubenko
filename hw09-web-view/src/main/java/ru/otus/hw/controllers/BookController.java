package ru.otus.hw.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.exceptions.NotFoundException;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.CommentService;
import ru.otus.hw.services.GenreService;

@RequiredArgsConstructor
@Controller
public class BookController {

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    private final CommentService commentService;


    @GetMapping("/books")
    public String allBooks(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "root";
    }

    @GetMapping(value = "/books", params = "title")
    public String searchByTitle(@RequestParam(required = false, defaultValue = "") String title, Model model) {
        model.addAttribute("title", title);
        model.addAttribute("books", bookService.findAllByTitleQuery(title));
        return "root";
    }

    @GetMapping("/books/{id}")
    public String getById(@PathVariable Long id, Model model) {
        var book = bookService.findById(id)
                .orElseThrow(() -> new NotFoundException("Book id = %d is not found".formatted(id)));

        model.addAttribute("book", new BookUpdateDto(
                book.getId(),
                book.getTitle(),
                book.getAuthor().getId(),
                book.getGenres().stream()
                        .map(GenreDto::getId).toList()
        ));
        model.addAttribute("allAuthors", authorService.findAll());
        model.addAttribute("allGenres", genreService.findAll());
        model.addAttribute("comments", commentService.findByBookId(book.getId()));

        return "books/book";
    }

    @DeleteMapping("/books/{id}")
    public String deleteById(@PathVariable Long id) {
        bookService.deleteById(id);

        return "redirect:/books";
    }


    @GetMapping("/books/create")
    public String createBook(Model model) {
        var authors = authorService.findAll();
        var book = new BookCreateDto();
        book.setAuthorId(authors.get(0).getId());
        book.setTitle("");

        model.addAttribute("book", book);
        model.addAttribute("allAuthors", authors);
        model.addAttribute("allGenres", genreService.findAll());

        return "books/book";
    }

    @PostMapping("/books/")
    public String createBook(@Valid @ModelAttribute("book") BookCreateDto book) {
        var bookId = bookService.insert(book).getId();
        return "redirect:/books/" + bookId;
    }

    @PutMapping("/books/{bookId}")
    public String updateBook(@Valid @ModelAttribute("book") BookUpdateDto book, @PathVariable String bookId) {
        bookService.update(book).getId();
        return "redirect:/books/" + bookId;
    }
}
