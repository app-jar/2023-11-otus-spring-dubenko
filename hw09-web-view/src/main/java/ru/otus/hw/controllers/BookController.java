package ru.otus.hw.controllers;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.hw.dto.BookEditDto;
import ru.otus.hw.exceptions.BadRequestException;
import ru.otus.hw.exceptions.NotFoundException;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.CommentService;
import ru.otus.hw.services.GenreService;

import java.util.List;

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

        model.addAttribute("book", book);
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
        var book = new BookEditDto();
        var authors = authorService.findAll();
        model.addAttribute("book", book);
        model.addAttribute("allAuthors", authors);
        model.addAttribute("allGenres", genreService.findAll());

        book.setGenreIds(List.of());
        book.setAuthorId(authors.get(0).getId());
        book.setTitle("");

        return "books/book";
    }

    @PostMapping("/books/save")
    public String updateBook(@ModelAttribute("book") BookEditDto book) {
        try {
            Long bookId = null;
            if (book.getId() == 0) {
                bookId = bookService.insert(book).getId();
            } else {
                bookId = bookService.update(book).getId();
            }
            return "redirect:/books/" + bookId;
        } catch (EntityNotFoundException | IllegalArgumentException ex) {
            throw new BadRequestException(ex.getMessage());
        }
    }
}
